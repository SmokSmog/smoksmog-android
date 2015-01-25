package pl.malopolska.smoksmog.data;


import dagger.Component;

@Component(
        modules = {
                NetworkModule.class
        }
)
public interface DataManager {

    public DataProvider data();
}
