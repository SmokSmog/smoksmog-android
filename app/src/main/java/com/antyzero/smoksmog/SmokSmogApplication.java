package com.antyzero.smoksmog;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.antyzero.smoksmog.database.SmokSmokDb;
import com.antyzero.smoksmog.database.model.ListItemDb;
import com.antyzero.smoksmog.job.SmokSmogJobService;
import com.antyzero.smoksmog.tracking.Tracking;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import smoksmog.logger.Logger;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SmokSmogApplication extends Application {

    private static final String TAG = SmokSmogApplication.class.getSimpleName();

    @Inject
    Logger logger;
    @Inject
    SmokSmokDb smokSmokDb;
    @Inject
    FirebaseJobDispatcher dispatcher;
    @Inject
    Tracking tracking;

    private ApplicationComponent applicationComponent;

    /**
     * Get access to application instance
     *
     * @param context for accessing application object
     * @return SmokSmogApplication object
     */
    public static SmokSmogApplication get(Context context) {
        return (SmokSmogApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build(), new Answers());

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);
        logger.i(TAG, "First run: " + tracking.getFirstRunDateTime());

        //noinspection SpellCheckingInspection
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Light.ttf")
                .build());


        // Periodical jobs

        Job job = dispatcher.newJobBuilder()
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setService(SmokSmogJobService.class)
                .setTag("station-widget-refresh")
                .setTrigger(Trigger.executionWindow(5 * 60, 5 * 60 + 60))
                .build();

        int result = dispatcher.schedule(job);

        if (result != FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS) {
            logger.w(TAG, "Unable to schedule widget update job");
        }

        // TODO db testing code ignore and delete in future

        /*
        smokSmokDb.getList()
                .subscribe(listItemDb -> {
                    System.out.println(String.format(">>> id:%s | p:%s", listItemDb._id(), listItemDb.position()));
                });

        smokSmokDb.addToList(ListItemDb.FACTORY.marshal()._id(1));
        smokSmokDb.addToList(ListItemDb.FACTORY.marshal()._id(2));
        smokSmokDb.addToList(ListItemDb.FACTORY.marshal()._id(3));
        */

    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    /**
     * Kept to inject mocked components.
     *
     * @param applicationComponent for replace
     */
    @VisibleForTesting
    public void setAppComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
