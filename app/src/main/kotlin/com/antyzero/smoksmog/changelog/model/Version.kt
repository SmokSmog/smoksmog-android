package com.antyzero.smoksmog.changelog.model

import org.joda.time.LocalDate


data class Version(
        val name: String,
        val code: Int,
        val date: LocalDate,
        val changes: List<Change> = listOf())