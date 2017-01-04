package smoksmog.logger


import java.util.Arrays

class AggregatingLogger(private val loggerCollection: Collection<Logger>) : Logger {

    constructor(vararg logger: Logger) : this(Arrays.asList(*logger)) {
    }

    override fun v(tag: String, message: String) {
        for (logger in loggerCollection) {
            logger.v(tag, message)
        }
    }

    override fun v(tag: String, message: String, throwable: Throwable) {
        for (logger in loggerCollection) {
            logger.v(tag, message, throwable)
        }
    }

    override fun d(tag: String, message: String) {
        for (logger in loggerCollection) {
            logger.d(tag, message)
        }
    }

    override fun d(tag: String, message: String, throwable: Throwable) {
        for (logger in loggerCollection) {
            logger.d(tag, message, throwable)
        }
    }

    override fun i(tag: String, message: String) {
        for (logger in loggerCollection) {
            logger.i(tag, message)
        }
    }

    override fun i(tag: String, message: String, throwable: Throwable) {
        for (logger in loggerCollection) {
            logger.i(tag, message, throwable)
        }
    }

    override fun w(tag: String, message: String) {
        for (logger in loggerCollection) {
            logger.w(tag, message)
        }
    }

    override fun w(tag: String, message: String, throwable: Throwable) {
        for (logger in loggerCollection) {
            logger.w(tag, message, throwable)
        }
    }

    override fun e(tag: String, message: String) {
        for (logger in loggerCollection) {
            logger.e(tag, message)
        }
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
        for (logger in loggerCollection) {
            logger.e(tag, message, throwable)
        }
    }
}
