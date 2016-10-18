package smoksmog.logger;


import java.util.Arrays;
import java.util.Collection;

public class AggregatingLogger implements Logger {

    private final Collection<Logger> loggerCollection;

    public AggregatingLogger(Logger... logger) {
        this(Arrays.asList(logger));
    }

    public AggregatingLogger(Collection<Logger> loggerCollection) {
        this.loggerCollection = loggerCollection;
    }

    @Override
    public void v(String tag, String message) {
        for (Logger logger : loggerCollection) {
            logger.v(tag, message);
        }
    }

    @Override
    public void v(String tag, String message, Throwable throwable) {
        for (Logger logger : loggerCollection) {
            logger.v(tag, message, throwable);
        }
    }

    @Override
    public void d(String tag, String message) {
        for (Logger logger : loggerCollection) {
            logger.d(tag, message);
        }
    }

    @Override
    public void d(String tag, String message, Throwable throwable) {
        for (Logger logger : loggerCollection) {
            logger.d(tag, message, throwable);
        }
    }

    @Override
    public void i(String tag, String message) {
        for (Logger logger : loggerCollection) {
            logger.i(tag, message);
        }
    }

    @Override
    public void i(String tag, String message, Throwable throwable) {
        for (Logger logger : loggerCollection) {
            logger.i(tag, message, throwable);
        }
    }

    @Override
    public void w(String tag, String message) {
        for (Logger logger : loggerCollection) {
            logger.w(tag, message);
        }
    }

    @Override
    public void w(String tag, String message, Throwable throwable) {
        for (Logger logger : loggerCollection) {
            logger.w(tag, message, throwable);
        }
    }

    @Override
    public void e(String tag, String message) {
        for (Logger logger : loggerCollection) {
            logger.e(tag, message);
        }
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        for (Logger logger : loggerCollection) {
            logger.e(tag, message, throwable);
        }
    }
}
