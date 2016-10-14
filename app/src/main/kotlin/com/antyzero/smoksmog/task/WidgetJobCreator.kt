package com.antyzero.smoksmog.task

import android.content.Context
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class WidgetJobCreator(val context: Context) : JobCreator {

    override fun create(tag: String?): Job? {

        return when (tag) {
            "StationWidgetJob" -> StationWidgetJob()
            else -> null
        }
    }

}