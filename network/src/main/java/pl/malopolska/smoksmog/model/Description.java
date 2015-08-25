package pl.malopolska.smoksmog.model;

import lombok.Getter;

@Getter
public class Description {

    private String desc;

    @Override
    public String toString() {
        return desc;
    }
}
