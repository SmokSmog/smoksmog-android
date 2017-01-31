package com.antyzero.smoksmog.job

import com.antyzero.smoksmog.dsl.appComponent
import com.antyzero.smoksmog.ui.widget.StationWidgetService
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class SmokSmogJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        StationWidgetService.updateAll(this)
        return false
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }
}
