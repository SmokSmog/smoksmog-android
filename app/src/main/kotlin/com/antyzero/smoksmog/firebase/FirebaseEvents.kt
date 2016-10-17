package com.antyzero.smoksmog.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseEvents(private val firebaseAnalytics: FirebaseAnalytics) {

    fun logStationCardInView(stationId: Long) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, Bundle().apply {
            setItemId(stationId)
            setContentType(Content.STATION)
            // TODO station name ?
        })
    }

    fun logWidgetCreationStarted() {
        firebaseAnalytics.logEvent("widget-station-creation-started", Bundle())
    }

    fun logWidgetCreationStation(stationId: Long, stationName: String) {
        firebaseAnalytics.logEvent("widget-station-creation-station-id", Bundle().apply {
            setItemId(stationId)
            setItemName(stationName)
        })
    }

    fun logWidgetCreationSuccessful() {
        firebaseAnalytics.logEvent("widget-station-creation-successful", Bundle())
    }
}

private fun Bundle.setContentType(content: Content) = this.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content.toString())
private fun Bundle.setItemId(id: Long) = putLong(FirebaseAnalytics.Param.ITEM_ID, id)
private fun Bundle.setItemName(stationName: String) = this.putString(FirebaseAnalytics.Param.ITEM_NAME, stationName)

private enum class Content(private val contentName: String) {

    STATION("station");

    override fun toString(): String {
        return contentName
    }
}
