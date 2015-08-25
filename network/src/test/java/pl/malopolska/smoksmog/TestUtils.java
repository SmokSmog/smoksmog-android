package pl.malopolska.smoksmog;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by iwopolanski on 25.08.15.
 */
public class TestUtils {

    private TestUtils() {
        throw new IllegalAccessError( "Utils class" );
    }

    public static String readFromResources( String pathFile ) {

        String path = null;

        try {
            final URL resource = TestUtils.class.getResource( pathFile );

            if ( resource == null ) {
                throw new IllegalArgumentException( "Unable to find resource for path \"" + pathFile + "\"" );
            }

            path = resource.getPath();

            return readFileToString( path, Charset.defaultCharset() );
        } catch ( IOException e ) {
            throw new RuntimeException( "Unable to read resource file at " + path, e );
        }
    }

    public static String readFileToString( String path, Charset charset ) throws IOException {
        List<String> lines = Files.readAllLines( Paths.get( path ), charset );
        return join("",lines);
    }

    private static String join(String delim, Collection<?> col) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> iter = col.iterator();
        if (iter.hasNext())
            sb.append(iter.next().toString());
        while (iter.hasNext()) {
            sb.append(delim);
            sb.append(iter.next().toString());
        }
        return sb.toString();
    }
}
