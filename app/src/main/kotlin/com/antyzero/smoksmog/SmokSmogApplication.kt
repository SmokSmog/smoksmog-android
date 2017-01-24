package com.antyzero.smoksmog

import android.app.Application
import android.content.Context
import com.antyzero.smoksmog.dsl.tag
import com.antyzero.smoksmog.job.SmokSmogJobService
import com.antyzero.smoksmog.tracking.Tracking
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.core.CrashlyticsCore
import com.firebase.jobdispatcher.*
import io.fabric.sdk.android.Fabric
import smoksmog.logger.Logger
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

class SmokSmogApplication : Application() {

    @Inject lateinit var logger: Logger
    @Inject lateinit var dispatcher: FirebaseJobDispatcher
    @Inject lateinit var tracking: Tracking

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build(), Answers())

        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        appComponent.inject(this)
        logger.i(tag(), "First run: " + tracking.getFirstRunDateTime())


        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Light.ttf")
                .build())


        // Periodical jobs

        val job = dispatcher.newJobBuilder()
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setService(SmokSmogJobService::class.java)
                .setTag("station-widget-refresh").apply {
            TriggerConfigurator.Companion.executionWindow(this, 5 * 60, 5 * 60 + 60)
        }.build()

        val result = dispatcher.schedule(job)

        if (result != FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS) {
            logger.w(tag(), "Unable to schedule widget update job")
        }
    }

    companion object {

        operator fun get(context: Context): SmokSmogApplication {
            return context.applicationContext as SmokSmogApplication
        }
    }
}
