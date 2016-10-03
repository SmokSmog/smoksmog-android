package pl.malopolska.smoksmog

import pl.malopolska.smoksmog.model.Description
import pl.malopolska.smoksmog.model.Station
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface Api {

    @GET("/stations")
    fun stations(): Observable<List<Station>>

    @GET("/stations/{stationId}")
    fun station(@Path("stationId") stationId: Long): Observable<Station>

    @GET("/stations/{lat}/{lon}")
    fun stationByLocation(@Path("lat") latitude: Double, @Path("lon") longitude: Double): Observable<Station>

    @GET("/stations/{stationId}/history")
    fun stationHistory(@Path("stationId") stationId: Long): Observable<Station>

    @GET("/stations/{lat}/{lon}/history")
    fun stationHistoryByLocation(@Path("lat") latitude: Double, @Path("lon") longitude: Double): Observable<Station>

    @GET("/particulates/{id}/desc")
    fun particulateDescription(@Path("id") particulateId: Long): Observable<Description>
}
