package com.antyzero.smoksmog.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.antyzero.smoksmog.database.model.ListItemDb;
import com.squareup.sqlbrite.SqlBrite;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Random;

import rx.Observable;
import rx.functions.Func2;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(AndroidJUnit4.class)
public class SmokSmokDbTest {

    private SmokSmogSQLiteOpenHelper openHelper;
    private SqlBrite sqlBrite;
    private SmokSmokDb smokSmokDb;
    private Context context;

    @Test
    public void testInitData() throws Exception {

        // Given
        TestSubscriber<ListItemDb> testSubscriber = new TestSubscriber<>();

        // When
        smokSmokDb.getList().subscribe(testSubscriber);

        // Then
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);

        ListItemDb item = testSubscriber.getOnNextEvents().get(0);
        assertThat(item._id()).isEqualTo(0);
        assertThat(item.position()).isEqualTo(0);
        assertThat(item.visible()).isEqualTo(false);
    }

    @Test
    public void testProperPositionValues() throws Exception {

        // Given
        TestSubscriber<ListItemDb> testSubscriber = new TestSubscriber<>();
        Random random = new Random(123);
        for (int i = 0; i < 10; i++) {
            smokSmokDb.addToList(ListItemDb.FACTORY.marshal()
                    ._id(i + 20).position(random.nextInt()).visible(random.nextBoolean()));
        }
        smokSmokDb.removeFromList(21);
        smokSmokDb.removeFromList(26);
        smokSmokDb.removeFromList(29);

        smokSmokDb.addToList(ListItemDb.FACTORY.marshal()
                ._id(21).position(random.nextInt()).visible(random.nextBoolean()));
        smokSmokDb.addToList(ListItemDb.FACTORY.marshal()
                ._id(22).position(random.nextInt()).visible(random.nextBoolean())); // Existing

        // When
        smokSmokDb.getList().subscribe(testSubscriber);

        // Then
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(9);

        Observable.from(testSubscriber.getOnNextEvents())
                .reduce((first, second) -> {
                    assertThat(second.position() - first.position()).isEqualTo(1);
                    return second;
                }).subscribe();

    }

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext().getApplicationContext();
        File file = context.getDatabasePath(SmokSmogSQLiteOpenHelper.DB_NAME);
        if (file != null && file.exists()) {
            if (file.delete()) {
                System.out.print("Delete db");
            }
        }
        openHelper = new SmokSmogSQLiteOpenHelper(context);
        sqlBrite = SqlBrite.create();
        smokSmokDb = new SmokSmokDb(sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io()));
    }

    @After
    public void tearDown() throws Exception {
        smokSmokDb = null;
        sqlBrite = null;
        openHelper.close();
        context.deleteDatabase(openHelper.getDatabaseName());
        openHelper = null;
        context = null;
    }
}