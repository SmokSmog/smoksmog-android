package com.antyzero.smoksmog.storage

import com.antyzero.smoksmog.storage.model.Item


interface Storage {

    fun addStation(id: Long): Boolean {
        return add(Item.Station(id))
    }

    fun add(item: Item): Boolean

    fun removeById(id: Long)

    fun update(id: Long, itemUpdate: Item)

    fun fetchAll(): List<Item>
}