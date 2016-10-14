package com.antyzero.smoksmog.task

import android.content.ComponentName
import com.antyzero.smoksmog.appWidgetManager
import com.antyzero.smoksmog.ui.widget.StationWidget
import com.antyzero.smoksmog.ui.widget.StationWidgetService
import com.evernote.android.job.Job


class StationWidgetJob() : Job() {

    override fun onRunJob(params: Params?): Result {
        val ids = context.appWidgetManager().getAppWidgetIds(ComponentName(context, StationWidget::class.java))
        ids.forEach { StationWidgetService.update(context, it) }
        return Result.SUCCESS
    }
}