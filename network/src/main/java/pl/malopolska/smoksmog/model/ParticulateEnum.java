package pl.malopolska.smoksmog.model;

public enum ParticulateEnum {

    SO2( 1 ), NO2( 3 ), CO( 4 ), O3( 5 ), PM10( 7 ), C6H6( 1 ), UNKNOWN( -1 );

    private long id;

    ParticulateEnum( long id ) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static ParticulateEnum findById( long id ) {

        for ( ParticulateEnum particulateEnum : values() ) {
            if ( particulateEnum.id == id ) {
                return particulateEnum;
            }
        }

        ParticulateEnum unknown = UNKNOWN;
        unknown.id = id;
        return unknown;
    }

    @Override
    public String toString() {
        return name() + "{id=" + id + "}";
    }
}
