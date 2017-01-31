package com.antyzero.smoksmog.changelog

import com.antyzero.smoksmog.changelog.model.ChangeType
import com.antyzero.smoksmog.changelog.model.Changelog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import org.joda.time.LocalDate
import java.io.File
import java.util.*

class ChangelogReader(locale: Locale, changelogFile: File) {

    val changelog: Changelog

    private var gson: Gson

    init {
        gson = GsonBuilder().apply {
            registerTypeAdapter(LocalDate::class.java, JsonDeserializer<LocalDate> { json, typeOfT, context -> LocalDate(json.asLong) })
            registerTypeAdapter(ChangeType::class.java, JsonDeserializer<ChangeType> { json, typeOfT, context -> ChangeType.valueOf(json.asString.toUpperCase()) })
        }.create()

        changelog = gson.fromJson(changelogFile.readText(), Changelog::class.java)
    }
}