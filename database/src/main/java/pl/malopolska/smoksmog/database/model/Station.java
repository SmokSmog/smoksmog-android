package pl.malopolska.smoksmog.database.model;

public class Station {

    private final Long id;

    private final String name;

    private final float latitude;

    private final float longitude;

    private final int hash;

    public Station(String name, float latitude, float longitude) {
        this(null, name, latitude, longitude);
    }

    public Station(Long id, String name, float latitude, float longitude) {

        if (name == null) {
            throw new IllegalArgumentException("Null name not allowed");
        }

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;

        this.hash = hashCode();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (Float.compare(station.latitude, latitude) != 0) return false;
        if (Float.compare(station.longitude, longitude) != 0) return false;
        if (!name.equals(station.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        return result;
    }
}
