package pl.malopolska.smoksmog.database;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.test.ApplicationTestCase;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class SmogSmokDatabaseHelperTest extends ApplicationTestCase<Application> {

    public SmogSmokDatabaseHelperTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();

        File file = getApplication().getDatabasePath(SmogSmokDatabaseHelper.DATABASE_NAME);

        if( file.exists() ){
            if( !file.delete() ){
                throw new IllegalStateException( "Unable to delete old database file" );
            }
        }
    }

    public void testDatabaseCreationProcess() throws Exception {

        SmogSmokDatabaseHelper databaseHelper = new SmogSmokDatabaseHelper( getApplication() );

        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        assertThat( database ).isNotNull();
    }
}