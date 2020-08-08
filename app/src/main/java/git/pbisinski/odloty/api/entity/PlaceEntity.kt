package git.pbisinski.odloty.api.entity

import com.google.gson.annotations.SerializedName
import git.pbisinski.odloty.api.model.PlaceModel

class PlaceEntity(
  @SerializedName("PlaceId")
  val placeId: String,
  @SerializedName("PlaceName")
  val placeName: String,
  @SerializedName("CountryId")
  val countryId: String,
  @SerializedName("RegionId")
  val regionId: String,
  @SerializedName("CityId")
  val cityId: String,
  @SerializedName("CountryName")
  val countryName: String
)  {

  companion object {
    fun toModel(entity: PlaceEntity): PlaceModel {
      return PlaceModel(
        placeId = entity.placeId,
        placeName = entity.placeName,
        countryId = entity.countryId,
        regionId = entity.regionId,
        cityId = entity.cityId,
        countryName = entity.countryName
      )
    }
  }
}