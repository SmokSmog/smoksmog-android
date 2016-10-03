package pl.malopolska.smoksmog.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import java.util.*

data class Particulate(
        val id: Long,
        val name: String,
        @SerializedName("short_name") val shortName: String,
        val value: Float = 0f,
        val unit: String,
        val norm: Float = 0f,
        val date: DateTime,
        @SerializedName("avg") val average: Float = 0f,
        val position: Int = 0,
        val values: List<History> = ArrayList<History>()) {

    val enum: ParticulateEnum
        get() = ParticulateEnum.findById(id)
}
