package com.antyzero.smoksmog.changelog.model


data class Change(
        val type: ChangeType,
        val text: String,
        val translation: Map<String, String> = mapOf())