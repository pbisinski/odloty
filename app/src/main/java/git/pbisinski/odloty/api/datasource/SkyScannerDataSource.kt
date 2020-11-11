package git.pbisinski.odloty.api.datasource

import git.pbisinski.odloty.api.entity.PlacesEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SkyScannerDataSource {
  @GET("/apiservices/autosuggest/v1.0/{market}/{currency}/{locale}/")
  suspend fun getPlaces(
    @Path("market") market: String,
    @Path("currency") currency: String,
    @Path("locale") locale: String,
    @Query("query") query: String
  ): PlacesEntity
}
