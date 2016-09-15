package pl.malopolska.smoksmog

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.OkHttpClient
import org.joda.time.DateTime
import retrofit.RestAdapter
import retrofit.client.Client
import retrofit.client.OkClient
import retrofit.converter.GsonConverter
import java.util.*

open class SmokSmog(
        val endpoint: String = "http://api.smoksmog.jkostrz.name/",
        locale: Locale = Locale.getDefault(),
        client: Client? = OkClient(),
        debug: Boolean = false) {

    open val api: Api
    val gson: Gson

    init {
        val builderRest = RestAdapter.Builder()
        builderRest.setEndpoint(createEndpoint(endpoint, locale))

        if (debug) {
            builderRest.setLogLevel(RestAdapter.LogLevel.FULL)
        }

        if (client != null) {
            builderRest.setClient(client)
        }

        gson = createGson()
        builderRest.setConverter(GsonConverter(gson))
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
            return parse.newBuilder().addEncodedPathSegment(locale.language).toString()
        }
    }
}
