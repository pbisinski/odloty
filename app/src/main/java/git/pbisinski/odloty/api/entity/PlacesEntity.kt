package git.pbisinski.odloty.api.entity

import com.google.gson.annotations.SerializedName

data class PlacesEntity(
  @SerializedName("Places")
  val places: List<PlaceEntity>
)
