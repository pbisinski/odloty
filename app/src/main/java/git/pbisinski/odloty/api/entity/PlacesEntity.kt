package git.pbisinski.odloty.api.entity

import com.google.gson.annotations.SerializedName
import git.pbisinski.odloty.api.model.PlacesModel

data class PlacesEntity(
  @SerializedName("Places")
  val places: List<PlaceEntity>?
) {
  companion object Mapper {
    fun toModel(entity: PlacesEntity): PlacesModel {
      return PlacesModel(
        places = entity.places?.map(PlaceEntity.Mapper::toModel) ?: error("places missing")
      )
    }
  }
}
