package pl.malopolska.smoksmog

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.util.*

open class SmokSmog(locale: Locale = Locale.getDefault(), serverUrl: String = ENDPOINT) {

    constructor(locale: Locale = Locale.getDefault()) : this(locale, ENDPOINT)

    constructor() : this(Locale.getDefault())

    val endpoint: String = "$serverUrl${locale.language}/"

    open val api: Api

    init {

        val gsonBuilder = GsonBuilder().apply {
            registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
            Converters.registerLocalDate(this)
        }

        api = Retrofit.Builder().apply {
            baseUrl(endpoint)
            addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.newThread()))
            client(OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder().apply {
                        header("X-Smog-AdditionalMeasurements".toLowerCase(), "pm25")
                        method(original.method(), original.body())
                    }.build()
                    chain.proceed(request)
                }
            }.build())
        }.build().create(Api::class.java)
    }

    companion object {
        val ENDPOINT = "http://api.smoksmog.jkostrz.name/"
    }

}
