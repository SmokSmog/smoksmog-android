package com.firebase.jobdispatcher


class TriggerConfigurator {
    companion object {
        fun executionWindow(builder: Job.Builder, windowStart: Int, windowEnd: Int) {
            builder.trigger = Trigger.executionWindow(windowStart, windowEnd)
        }
    }
}