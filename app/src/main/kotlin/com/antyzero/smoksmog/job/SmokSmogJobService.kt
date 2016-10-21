package com.antyzero.smoksmog.job

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class SmokSmogJobService() : JobService() {

    override fun onStopJob(job: JobParameters?): Boolean {
        // Do some work here

        return false // TODO Answers the question: "Is there still work going on?"
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        return false // TODO Answers the question: "Should this job be retried?"
    }
}