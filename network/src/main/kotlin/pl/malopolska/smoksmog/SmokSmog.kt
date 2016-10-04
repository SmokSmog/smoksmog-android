package pl.malopolska.smoksmog

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

open class SmokSmog(locale: Locale = Locale.getDefault(), serverUrl: String = "http://api.smoksmog.jkostrz.name/") {

    constructor(locale: Locale = Locale.getDefault()) : this(locale, "http://api.smoksmog.jkostrz.name/")

    val gson: Gson
    val endpoint: String = "$serverUrl${locale.language}/"
    open val api: Api

    init {

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
        Converters.registerLocalDate(gsonBuilder)
        gson = gsonBuilder.create()

        val builderRest = Retrofit.Builder()
        builderRest.baseUrl(endpoint)
        builderRest.addConverterFactory(GsonConverterFactory.create(gson))
        builderRest.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        val restAdapter = builderRest.build()
        api = restAdapter.create(Api::class.java)
    }
}
