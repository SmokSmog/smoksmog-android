package rx.plugins;

/**
 * Allows to access reser
 */
public class RxJavaTestPlugins extends RxJavaPlugins {

    RxJavaTestPlugins() {
        super();
    }

    public static void resetPlugins(){
        getInstance().reset();
    }
}
