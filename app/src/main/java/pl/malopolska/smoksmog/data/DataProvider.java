package pl.malopolska.smoksmog.data;

import javax.inject.Inject;

import pl.malopolska.smoksmog.network.SmokSmogAPI;

public class DataProvider {

    private final SmokSmogAPI smogAPI;

    @Inject
    public DataProvider( SmokSmogAPI smogAPI ) {
        this.smogAPI = smogAPI;
    }

    public SmokSmogAPI getSmokSmogAPI() {
        return smogAPI;
    }
}
