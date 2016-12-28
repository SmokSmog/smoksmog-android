package com.antyzero.smoksmog.storage.model

import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import com.google.gson.JsonParseException

sealed class Item(val id: Long, val modules: MutableSet<in Module>) {

    private val _class: String = javaClass.canonicalName

    /**
     * Single, if present this represent nearest station
     */
    class Nearest(modules: MutableSet<in Module> = mutableSetOf()) : Item(0, modules) {

        fun copy(modules: MutableSet<in Module> = this.modules): Nearest = Nearest(modules)
    }

    /**
     * Multiple, this represent station item
     */
    class Station(id: Long = 1, modules: MutableSet<in Module> = mutableSetOf()) : Item(id, modules) {

        init {
            require(isIdAllowed(id), { "For station {id} only values greater than 0 are acceptable" })
        }

        fun copy(id: Long = this.id, modules: MutableSet<in Module> = this.modules): Station = Station(id, modules)

        companion object {

            fun isIdAllowed(id: Long): Boolean = id > 0
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Item) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()

    companion object {

        fun deserializer(): JsonDeserializer<Item> = JsonDeserializer { json, typeOfT, context ->

            if (json is JsonObject) {
                val id = json.get("id").asLong
                return@JsonDeserializer when {
                    id > 0 -> Station(id)
                    id == 0L -> Nearest()
                    else -> throw JsonParseException("Unsupported id value: $id")
                }
            }
            throw JsonParseException("Unable to parse")
        }
    }
}