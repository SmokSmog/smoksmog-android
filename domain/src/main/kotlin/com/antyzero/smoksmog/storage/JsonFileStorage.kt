package com.antyzero.smoksmog.storage

import com.antyzero.smoksmog.storage.model.Item
import com.antyzero.smoksmog.storage.model.Module
import com.antyzero.smoksmog.storage.model.Module.AirQualityIndex
import com.antyzero.smoksmog.storage.model.Module.AirQualityIndex.Type.POLISH
import com.antyzero.smoksmog.storage.model.Module.Measurements
import com.google.gson.*
import java.io.File
import java.lang.reflect.Type

class JsonFileStorage(val file: File = File.createTempFile("jfs", "json")) : PersistentStorage {

    private val items: ItemList = ItemList()
    private val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Item::class.java, deserializeItem)
            .create()

    init {
        if (file.exists().not()) {
            file.createNewFile()
        }

        file.readText().let {

            // Stick with that, we need to provide valid deserialization
            gson.fromJson(it, ItemList::class.java)?.let {
                items.addAll(it)
            }
            /*

            TODO: In future we may want to recreate file
            TODO: Send some kind of message to the system, pass to user to avoid surprise

            try {

            } catch (e: Exception) {
                with(file) {
                    delete()
                    createNewFile()
                }
            }
            */
        }
    }

    override fun clear() {
        items.clear()
        saveItems()
    }

    override fun add(item: Item): Boolean {
        return if (containsItem(item)) {
            false
        } else {
            return when (item) {
                is Item.Nearest -> {
                    items.add(0, item)
                    true
                }
                else -> items.add(item)
            }.apply { saveItems() }
        }
    }

    override fun removeById(id: Long) {
        items.filter { it.id == id }.forEach { items.remove(it) }
        saveItems()
    }

    override fun update(id: Long, item: Item) {

        val (index, foundItem) = items.mapIndexed { i, item -> i to item }
                .filter { it.second.id == id }
                .first()

        items[index] = when (item) {
            is Item.Station -> item.copy(id = foundItem.id, modules = item.modules)
            is Item.Nearest -> item.copy(modules = item.modules)
            else -> throw IllegalStateException("This item type (${item.javaClass} is not supported)")
        }
        saveItems()
    }

    override fun fetchAll(): List<Item> = items

    private fun containsItem(item: Item): Boolean {
        return items.filter { it.id == item.id }.isNotEmpty()
    }

    private fun saveItems() {
        with(file) {
            delete()
            if (items.size > 0) {
                gson.toJson(items).let {
                    if (it.length > 4) {
                        writeText(it)
                    }
                }
            }
        }
    }

    /**
     * Private item deserializator
     */
    private object deserializeItem : JsonDeserializer<Item> {

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Item {
            if (json is JsonObject) {
                val clazz = json["_class"].asString
                val id = json["id"].asLong
                val modules = deserializeModules(json["modules"].asJsonArray)
                // TODO deserialize modules

                return when (clazz) {
                    "com.antyzero.smoksmog.storage.model.Item.Nearest" -> Item.Nearest(modules)
                    "com.antyzero.smoksmog.storage.model.Item.Station" -> Item.Station(id, modules)
                    else -> throw JsonParseException("Unsupported type $clazz")
                }
            }

            throw JsonParseException("Unable to deserialize")
        }

        private fun deserializeModules(jsonArray: JsonArray): MutableSet<Module> {
            return jsonArray
                    .filterIsInstance(JsonObject::class.java)
                    .map {
                        when (it["_class"].asString) {
                            "com.antyzero.smoksmog.storage.model.Module.AirQualityIndex" -> AirQualityIndex(POLISH)
                            "com.antyzero.smoksmog.storage.model.Module.Measurements" -> Measurements()
                            else -> throw JsonParseException("Unsupported type ${it["_class"].asString}")
                        }
                    }.toMutableSet()
        }
    }

    private class ItemList(private val list: MutableList<Item> = mutableListOf()) : MutableList<Item> by list
}