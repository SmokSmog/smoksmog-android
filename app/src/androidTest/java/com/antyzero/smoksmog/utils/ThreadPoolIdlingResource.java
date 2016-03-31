package com.antyzero.smoksmog.utils;


import android.support.test.espresso.IdlingResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkState;

public abstract class ThreadPoolIdlingResource implements IdlingResource {

    private static final String TAG = "ThreadPoolIdlingResource";

    private final AtomicReference<IdleMonitor> monitor = new AtomicReference<>( null );
    private final AtomicInteger activeBarrierChecks = new AtomicInteger( 0 );
    private final ThreadPoolExecutor threadPoolExecutor;
    private final List<ResourceCallback> callbacks = new ArrayList<>();

    private AtomicBoolean isMonitorForIdle = new AtomicBoolean( false );

    private final Runnable idleAction = new Runnable() {
        @Override
        public void run() {
            try {
                isMonitorForIdle.set( false );
                for ( ResourceCallback callback : callbacks ) {
                    callback.onTransitionToIdle();
                }
            } finally {
                cancelIdleMonitor();
            }
        }
    };

    protected ThreadPoolIdlingResource( ThreadPoolExecutor executor ) {
        this.threadPoolExecutor = checkNotNull( executor,
                String.format( "Trying to instantiate a \'%s\' with a null pool", getName() ) );
    }

    /**
     * Checks if the minPoolThreads is idle at this moment.
     *
     * @return true if the minPoolThreads is idle, false otherwise.
     */
    @Override
    public boolean isIdleNow() {
        // The minPoolThreads executor hasn't been injected yet, so we're idling
        if ( threadPoolExecutor == null ) {
            return true;
        }
        boolean idle;
        if ( !threadPoolExecutor.getQueue().isEmpty() ) {
            idle = false;
        } else {
            int activeCount = threadPoolExecutor.getActiveCount();
            if ( 0 != activeCount ) {
                if ( monitor.get() == null ) {
                    // if there's no idle monitor scheduled and there are still barrier
                    // checks running, they are about to exit, ignore them.
                    activeCount = activeCount - activeBarrierChecks.get();
                }
            }
            idle = 0 == activeCount;
        }

        if ( !idle && !isMonitorForIdle.get() ) {
            isMonitorForIdle.set( true );
            notifyWhenIdle( idleAction );
        }

        return idle;
    }

    @Override
    public void registerIdleTransitionCallback( final ResourceCallback resourceCallback ) {
        this.callbacks.add( resourceCallback );
    }

    /**
     * Notifies caller once the minPoolThreads is idle.
     * <p/>
     * We check for idle-ness by submitting the max # of tasks the minPoolThreads will take and
     * blocking
     * the tasks until they are all executing. Then we know there are no other tasks _currently_
     * executing in the minPoolThreads, we look back at the work queue to see if its backed up,
     * if it is
     * we reenqueue ourselves and try again.
     * <p/>
     * Obviously this strategy will fail horribly if 2 parties are doing it at the same time,
     * we prevent recursion here the best we can.
     *
     * @param idleCallback called once the minPoolThreads is idle.
     */
    void notifyWhenIdle( final Runnable idleCallback ) {
        checkNotNull( idleCallback );
        IdleMonitor myMonitor = new IdleMonitor( idleCallback );
        checkState( monitor.compareAndSet( null, myMonitor ), "cannot monitor for idle recursively!" );
        myMonitor.monitorForIdle();
    }

    /**
     * Stops the idle monitoring mechanism if it is in place.
     * <p/>
     * Note: the callback may still be invoked after this method is called. The only thing
     * this method guarantees is that we will stop/cancel any blockign tasks we've placed
     * on the thread minPoolThreads.
     */
    void cancelIdleMonitor() {
        IdleMonitor myMonitor = monitor.getAndSet( null );
        if ( null != myMonitor ) {
            myMonitor.poison();
        }
    }

    private class IdleMonitor {
        private final Runnable onIdle;
        private final AtomicInteger barrierGeneration = new AtomicInteger( 0 );
        private final CyclicBarrier barrier;
        // written by main, read by all.
        private volatile boolean poisoned;

        private IdleMonitor( final Runnable onIdle ) {
            this.onIdle = checkNotNull( onIdle );
            this.barrier = new CyclicBarrier( minPoolThreads(),
                    new Runnable() {
                        @Override
                        public void run() {
                            if ( threadPoolExecutor.getQueue().isEmpty() ) {
                                // no one is behind us, so the queue is idle!
                                monitor.compareAndSet( IdleMonitor.this, null );
                                onIdle.run();
                            } else {
                                // work is waiting behind us, enqueue another block of tasks and
                                // hopefully when they're all running, the queue will be empty.
                                monitorForIdle();
                            }

                        }
                    }
            );
        }

        private int minPoolThreads() {
            int corePoolSize = threadPoolExecutor.getCorePoolSize();
            return corePoolSize > 0 ? corePoolSize : Runtime.getRuntime().availableProcessors();
        }

        /**
         * Stops this monitor from using the thread minPoolThreads's resources,
         * it may still cause the
         * callback to be executed though.
         */
        private void poison() {
            poisoned = true;
            barrier.reset();
        }

        private void monitorForIdle() {
            if ( poisoned ) {
                return;
            }

            if ( isIdleNow() ) {
                monitor.compareAndSet( this, null );
                onIdle.run();
            } else {
                // Submit N tasks that will block until they are all running on the thread
                // minPoolThreads.
                // at this point we can check the minPoolThreads's queue and verify that there
                // are no new
                // tasks behind us and deem the queue idle.

                final BarrierRestarter restarter = new BarrierRestarter( barrier, barrierGeneration );
                for ( int i = 0; i < minPoolThreads(); i++ ) {
                    threadPoolExecutor.execute( new Runnable() {
                        @Override
                        public void run() {
                            while ( !poisoned ) {
                                activeBarrierChecks.incrementAndGet();
                                int myGeneration = barrierGeneration.get();
                                try {
                                    barrier.await();
                                    return;
                                } catch ( InterruptedException ie ) {
                                    // sorry - I cant let you interrupt me!
                                    restarter.restart( myGeneration );
                                } catch ( BrokenBarrierException bbe ) {
                                    restarter.restart( myGeneration );
                                } finally {
                                    activeBarrierChecks.decrementAndGet();
                                }
                            }
                        }
                    } );
                }
            }
        }
    }

    private static class BarrierRestarter {
        private final CyclicBarrier barrier;
        private final AtomicInteger barrierGeneration;

        BarrierRestarter( CyclicBarrier barrier, AtomicInteger barrierGeneration ) {
            this.barrier = barrier;
            this.barrierGeneration = barrierGeneration;
        }

        /**
         * restarts the barrier.
         * <p/>
         * After the calling this function it is guaranteed that barrier generation has been
         * incremented
         * and the barrier can be awaited on again.
         *
         * @param fromGeneration the generation that encountered the breaking exception.
         */
        synchronized void restart( int fromGeneration ) {
            // must be synchronized. T1 could pass the if check, be suspended before calling
            // reset, T2
            // sails thru - and awaits on the barrier again before T1 has awoken and reset it.
            int nextGen = fromGeneration + 1;
            if ( barrierGeneration.compareAndSet( fromGeneration, nextGen ) ) {
                // first time we've seen fromGeneration request a reset. lets reset the barrier.
                barrier.reset();
            } else {
                // some other thread has already reset the barrier - this request is a no op.
            }
        }
    }
}