package com.antyzero.smoksmog.storage.model

sealed class Module {

    private val _class: String = javaClass.canonicalName

    /**
     * Show AQI and it's type
     */
    class AirQualityIndex(val type: Type = AirQualityIndex.Type.POLISH) : Module() {

        enum class Type {
            POLISH
        }
    }

    /**
     * List of measurements for particulates
     */
    class Measurements : Module()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Module) return false

        if (_class != other._class) return false

        return true
    }

    override fun hashCode(): Int {
        return _class.hashCode()
    }
}