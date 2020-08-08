package git.pbisinski.odloty.api.entity

import com.google.gson.annotations.SerializedName

class PlacesEntity(
  @SerializedName("Places")
  val places: List<PlaceEntity>
)