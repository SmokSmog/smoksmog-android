package pl.malopolska.smoksmog

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

open class SmokSmog(
        val endpoint: String = "http://api.smoksmog.jkostrz.name/",
        locale: Locale = Locale.getDefault(),
        debug: Boolean = false) {

    open val api: Api
    val gson: Gson

    init {
        val builderRest = Retrofit.Builder()
        builderRest.baseUrl(createEndpoint(endpoint, locale))
        gson = createGson()
        builderRest.addConverterFactory(GsonConverterFactory.create(gson))
        builderRest.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        val restAdapter = builderRest.build()
        api = restAdapter.create(Api::class.java)
    }

    private fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
        Converters.registerLocalDate(gsonBuilder)
        return gsonBuilder.create()
    }

    companion object {

        fun createEndpoint(endpoint: String, locale: Locale): String {
            val parse = HttpUrl.parse(endpoint) ?: throw IllegalArgumentException("Invlid URL format")
            return parse.newBuilder().addEncodedPathSegment(locale.language).toString() + "/"
        }
    }
}
