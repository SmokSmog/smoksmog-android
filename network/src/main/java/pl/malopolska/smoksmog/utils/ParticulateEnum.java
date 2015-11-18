package pl.malopolska.smoksmog.utils;


public enum ParticulateEnum {

    SO_2(1),
    NO_2(3),
    CO(4),
    O_3(5),
    PM_10(7),
    C_6_H_6(11);

    private final int id;

    ParticulateEnum( int id ) {
        this.id = id;
    }
}
