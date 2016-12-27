package com.antyzero.smoksmog.job

import com.antyzero.smoksmog.appComponent
import com.antyzero.smoksmog.ui.widget.StationWidgetService
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import pl.malopolska.smoksmog.RestClient
import smoksmog.logger.Logger
import javax.inject.Inject

class SmokSmogJobService() : JobService() {

    @Inject lateinit var logger: Logger
    @Inject lateinit var restClient: RestClient

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