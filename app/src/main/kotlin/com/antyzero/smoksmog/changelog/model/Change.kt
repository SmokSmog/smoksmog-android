package com.antyzero.smoksmog.changelog.model


data class Change(
        val type: ChangeType,
        val text: String,
        val translations: Map<String, String> = mapOf())