package pl.malopolska.smoksmog.model

enum class ParticulateEnum(val id: Long) {

    SO2(1), NO(2), NO2(3), CO(4), O3(5), NOx(6), PM10(7), PM25(8), C6H6(11), UNKNOWN(-1);

    override fun toString() = "$name {id=$id}"

    companion object {

        fun findById(id: Long): ParticulateEnum {

            for (particulateEnum in values()) {
                if (particulateEnum.id == id) {
                    return particulateEnum
                }
            }
            return UNKNOWN
        }
    }
}
