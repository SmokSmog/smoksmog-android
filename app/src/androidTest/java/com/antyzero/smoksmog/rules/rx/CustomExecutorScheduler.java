package com.antyzero.smoksmog.rules.rx;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.internal.schedulers.GenericScheduledExecutorService;
import rx.internal.schedulers.ScheduledAction;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.MultipleAssignmentSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Copied from ExecutorScheduler class
 */
public class CustomExecutorScheduler extends Scheduler {

    final Executor executor;

    public CustomExecutorScheduler( Executor executor ) {
        this.executor = executor;
    }

    /**
     * @return
     * @warn javadoc missing
     */
    @Override
    public Worker createWorker() {
        return new ExecutorSchedulerWorker( executor );
    }

    /**
     * Worker that schedules tasks on the executor indirectly through a trampoline mechanism.
     */
    static final class ExecutorSchedulerWorker extends Worker implements Runnable {
        final Executor executor;
        // TODO: use a better performing structure for task tracking
        final CompositeSubscription tasks;
        // TODO: use MpscLinkedQueue once available
        final ConcurrentLinkedQueue<ScheduledAction> queue;
        final AtomicInteger wip;

        public ExecutorSchedulerWorker( Executor executor ) {
            this.executor = executor;
            this.queue = new ConcurrentLinkedQueue<>();
            this.wip = new AtomicInteger();
            this.tasks = new CompositeSubscription();
        }

        @Override
        public Subscription schedule( Action0 action ) {
            if ( isUnsubscribed() ) {
                return Subscriptions.unsubscribed();
            }
            ScheduledAction ea = new ScheduledAction( action, tasks );
            tasks.add( ea );
            queue.offer( ea );
            if ( wip.getAndIncrement() == 0 ) {
                try {
                    // note that since we schedule the emission of potentially multiple tasks
                    // there is no clear way to cancel this schedule from individual tasks
                    // so even if executor is an ExecutorService, we can't associate the future
                    // returned by submit() with any particular ScheduledAction
                    executor.execute( this );
                } catch ( RejectedExecutionException t ) {
                    // cleanup if rejected
                    tasks.remove( ea );
                    wip.decrementAndGet();
                    // report the error to the plugin
                    RxJavaPlugins.getInstance().getErrorHandler().handleError( t );
                    // throw it to the caller
                    throw t;
                }
            }

            return ea;
        }

        @Override
        public void run() {
            do {
                ScheduledAction sa = queue.poll();
                if ( !sa.isUnsubscribed() ) {
                    sa.run();
                }
            } while ( wip.decrementAndGet() > 0 );
        }

        @Override
        public Subscription schedule( final Action0 action, long delayTime, TimeUnit unit ) {
            if ( delayTime <= 0 ) {
                return schedule( action );
            }
            if ( isUnsubscribed() ) {
                return Subscriptions.unsubscribed();
            }
            ScheduledExecutorService service;
            if ( executor instanceof ScheduledExecutorService ) {
                service = ( ScheduledExecutorService ) executor;
            } else {
                service = GenericScheduledExecutorService.getInstance();
            }

            final MultipleAssignmentSubscription first = new MultipleAssignmentSubscription();
            final MultipleAssignmentSubscription mas = new MultipleAssignmentSubscription();
            mas.set( first );
            tasks.add( mas );
            final Subscription removeMas = Subscriptions.create( new Action0() {
                @Override
                public void call() {
                    tasks.remove( mas );
                }
            } );

            ScheduledAction ea = new ScheduledAction( new Action0() {
                @Override
                public void call() {
                    if ( mas.isUnsubscribed() ) {
                        return;
                    }
                    // schedule the real action untimed
                    Subscription s2 = schedule( action );
                    mas.set( s2 );
                    // unless the worker is unsubscribed, we should get a new ScheduledAction
                    if ( s2.getClass() == ScheduledAction.class ) {
                        // when this ScheduledAction completes, we need to remove the
                        // MAS referencing the whole setup to avoid leaks
                        ( ( ScheduledAction ) s2 ).add( removeMas );
                    }
                }
            } );
            // This will make sure if ea.call() gets executed before this line
            // we don't override the current task in mas.
            first.set( ea );
            // we don't need to add ea to tasks because it will be tracked through mas/first


            try {
                Future<?> f = service.schedule( ea, delayTime, unit );
                ea.add( f );
            } catch ( RejectedExecutionException t ) {
                // report the rejection to plugins
                RxJavaPlugins.getInstance().getErrorHandler().handleError( t );
                throw t;
            }

            /*
             * This allows cancelling either the delayed schedule or the actual schedule referenced
             * by mas and makes sure mas is removed from the tasks composite to avoid leaks.
             */
            return removeMas;
        }

        @Override
        public boolean isUnsubscribed() {
            return tasks.isUnsubscribed();
        }

        @Override
        public void unsubscribe() {
            tasks.unsubscribe();
        }

    }
}
