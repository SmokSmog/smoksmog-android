package pl.malopolska.smoksmog

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Scheduler
import java.util.*

class RestClient(val endpoint: String, api: Api) : Api by api {

    companion object {
        val ENDPOINT = "http://api.smoksmog.jkostrz.name/"

        fun createGson(): Gson = GsonBuilder().apply {
            registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
            Converters.registerLocalDate(this)
        }.create()
    }

    class Builder @JvmOverloads constructor(locale: Locale = Locale.getDefault(), serverUrl: String = ENDPOINT) {

        val endpoint: String = "$serverUrl${locale.language}/"

        var scheduler: Scheduler? = null
        var requestCustomizer: ((Request.Builder) -> Unit)? = null

        fun build(): RestClient {

            val api = Retrofit.Builder().apply {
                baseUrl(endpoint)
                addConverterFactory(GsonConverterFactory.create(createGson()))

                addCallAdapterFactory(if (scheduler == null) {
                    RxJavaCallAdapterFactory.create()
                } else {
                    RxJavaCallAdapterFactory.createWithScheduler(scheduler)
                })

                client(OkHttpClient.Builder().apply {
                    addInterceptor { chain ->
                        val original = chain.request()
                        val request = original.newBuilder().apply {
                            header("X-Smog-AdditionalMeasurements".toLowerCase(), "pm25")
                            requestCustomizer?.invoke(this)
                            method(original.method(), original.body())
                        }.build()
                        chain.proceed(request)
                    }
                }.build())
            }.build().create(Api::class.java)

            return RestClient(endpoint, api)
        }
    }
}
