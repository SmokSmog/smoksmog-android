package pl.malopolska.smoksmog.network;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import pl.malopolska.smoksmog.network.model.HistoryValue;
import pl.malopolska.smoksmog.network.model.ParticulateDescription;
import pl.malopolska.smoksmog.network.model.ParticulateDetails;
import pl.malopolska.smoksmog.network.model.ParticulateHistory;
import pl.malopolska.smoksmog.network.model.StationHistory;
import pl.malopolska.smoksmog.network.model.StationLocation;
import pl.malopolska.smoksmog.network.model.StationParticulates;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokSmogAPITest extends TestCase {

    private static final int STATION_ID = 4;
    public static final float LATITUDE = 50.061389f;
    public static final float LONGITUDE = 19.938333f;

    private SmokSmogAPI smokSmogAPI;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        smokSmogAPI = SmokSmogAPIImpl.create(
                "http://api.smoksmog.jkostrz.name/", new Locale("pl"));
    }

    public void testStations() throws Exception {

        Collection<StationLocation> stations = execute(smokSmogAPI.stations());

        assertThat( stations ).isNotNull().isNotEmpty();

        for( StationLocation stationLocation : stations ){
            TestUtils.invokeAllGetters( stationLocation );
        }
    }

    public void testStationById() throws Exception {

        StationParticulates station = execute(smokSmogAPI.station(STATION_ID));

        checkStationDetails( station );
    }

    public void testStationByLocation() throws Exception {

        StationParticulates station = execute(smokSmogAPI.station(LATITUDE, LONGITUDE));

        checkStationDetails( station );
    }

    public void testStationHistoryById() throws Exception {

        StationHistory station = execute(smokSmogAPI.stationHistory(STATION_ID));

        checkStationHistory( station );
    }

    public void testStationHistoryByLocation() throws Exception {

        StationHistory station = execute(smokSmogAPI.stationHistory(LATITUDE, LONGITUDE));

        checkStationHistory( station );
    }

    public void testParticulate() throws Exception {

        ParticulateDescription particulateDescription = execute(smokSmogAPI.particulate(1));

        assertThat( particulateDescription ).isNotNull();

        TestUtils.invokeAllGetters( particulateDescription );
    }

    private static <T> T execute(Observable<T> observable) throws InterruptedException {

        final List<T> result = new ArrayList<>();

        final Throwable[] throwable = {null};
        CountDownLatch countDownLatch = new CountDownLatch( 1 );

        observable
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new Observer<T>() {

                    @Override
                    public void onCompleted() {
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {
                        throwable[0] = e;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onNext(T t) {
                        result.add(t);
                    }
                });

        countDownLatch.await();

        if( throwable[0] != null ){
            throw new IllegalStateException( "Something went wrong", throwable[0]);
        }

        if( result.isEmpty() ){
            throw new IllegalStateException("Result is missing");
        }

        return result.get(0);
    }

    /**
     * Check station
     *
     * @param station with particulates information
     */
    private static void checkStationDetails( StationParticulates station ) {

        TestUtils.invokeAllGetters( station );

        assertThat( station ).isNotNull();

        Collection<ParticulateDetails> particulates = station.getParticulates();

        assertThat( particulates ).isNotNull().isNotEmpty();

        for ( ParticulateDetails particulate : particulates ) {
            TestUtils.invokeAllGetters( particulate );
            assertThat( particulate.getDate() ).isNotNull();
        }
    }

    private static void checkStationHistory( StationHistory station ) {

        TestUtils.invokeAllGetters( station );

        assertThat( station );

        Collection<ParticulateHistory> particulates = station.getParticulates();

        assertThat( particulates ).isNotNull().isNotEmpty();

        for ( ParticulateHistory particulate : particulates ) {

            TestUtils.invokeAllGetters( particulate );

            for ( HistoryValue historyValue : particulate.getValues() ) {

                TestUtils.invokeAllGetters( historyValue );

                assertThat( historyValue.getDate() ).isNotNull();
            }
        }
    }
}