package smoksmog.logger


interface Logger {

    // Verbose

    fun v(tag: String, message: String)

    fun v(tag: String, message: String, throwable: Throwable)

    // Debug

    fun d(tag: String, message: String)

    fun d(tag: String, message: String, throwable: Throwable)

    // Info

    fun i(tag: String, message: String)

    fun i(tag: String, message: String, throwable: Throwable)

    // Warning

    fun w(tag: String, message: String)

    fun w(tag: String, message: String, throwable: Throwable)

    // Error

    fun e(tag: String, message: String)

    fun e(tag: String, message: String, throwable: Throwable)
}
