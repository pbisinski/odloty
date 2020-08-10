package git.pbisinski.odloty.api.model

data class PlaceModel(
  val placeId: String,
  val placeName: String,
  val countryId: String,
  val regionId: String,
  val cityId: String,
  val countryName: String
)
