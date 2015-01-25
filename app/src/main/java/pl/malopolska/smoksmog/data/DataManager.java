package pl.malopolska.smoksmog.data;


import dagger.Component;
import pl.malopolska.smoksmog.network.SmokSmogAPI;

@Component(
        modules = {
                NetworkModule.class
        }
)
interface DataManager {

    SmokSmogAPI network();
}
