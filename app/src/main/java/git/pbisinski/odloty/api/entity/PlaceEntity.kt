package git.pbisinski.odloty.api.entity

import com.google.gson.annotations.SerializedName
import git.pbisinski.odloty.api.model.PlaceModel

data class PlaceEntity(
  @SerializedName("PlaceId")
  val placeId: String?,
  @SerializedName("PlaceName")
  val placeName: String?,
  @SerializedName("CountryId")
  val countryId: String?,
  @SerializedName("RegionId")
  val regionId: String?,
  @SerializedName("CityId")
  val cityId: String?,
  @SerializedName("CountryName")
  val countryName: String?
) {

  companion object Mapper {
    fun toModel(entity: PlaceEntity): PlaceModel {
      return PlaceModel(
        placeId = entity.placeId ?: error("placeId missing"),
        placeName = entity.placeName ?: error("placeName missing"),
        countryId = entity.countryId ?: error("countryId missing"),
        regionId = entity.regionId ?: error("regionId missing"),
        cityId = entity.cityId ?: error("cityId missing"),
        countryName = entity.countryName ?: error("countryName missing")
      )
    }
  }
}
