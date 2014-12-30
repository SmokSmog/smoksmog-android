package pl.malopolska.smoksmog.network;

import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Method;

/**
 * Created by iwopolanski on 22.12.14.
 */
public class TestUtils {

    private TestUtils() {
        throw new IllegalArgumentException( "Utility class" );
    }

    /**
     * @param o
     */
    public static void invokeAllGetters( Object o ) {
        invokeAllGetters( o, getPackageName( o ) );
    }

    /**
     * Call getters method on this object and
     *
     * @param o
     * @param packageScope
     */
    public static void invokeAllGetters( Object o, String packageScope ) {

        if ( o == null || ClassUtils.isPrimitiveOrWrapper( o.getClass() ) ) {
            return;
        }

        // We check only same package, avoid going to deep
        if ( !getPackageName( o ).equals( packageScope ) ) {
            return;
        }

        Method[] methods = o.getClass().getMethods();

        for ( Method method : methods ) {

            String methodName = method.getName();

            if ( isAccessorMethod( method ) ) {

                try {

                    // Invoke all getters in hierarchy
                    invokeAllGetters( method.invoke( o ), packageScope );

                } catch ( Exception e ) {

                    String message = String.format(
                            "Unable to execute method %s on object '%s' class %s",
                            methodName, o, o.getClass().getName() );

                    throw new IllegalArgumentException( message, e );
                }
            }
        }
    }

    private static String getPackageName( Object o ) {
        return o.getClass().getPackage().getName();
    }

    /**
     * Checks if Method object is representing accessor (getter) method. It should start with 'get' or
     * 'is' prefix and take no parameters.
     *
     * @param method for check
     * @return {@code true} if we have accessor method
     */
    private static boolean isAccessorMethod( Method method ) {
        return ( method.getName().startsWith( "get" ) || method.getName().startsWith( "is" ) ) && method.getParameterTypes().length == 0;
    }
}
