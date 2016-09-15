package pl.malopolska.smoksmog.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Station(

        val id: Long,
        val name: String,
        @SerializedName("lon") val longitude: Float = 0f,
        @SerializedName("lat") val latitude: Float = 0f) {

    val particulates: List<Particulate>? = ArrayList()


    override fun toString() = name
}
