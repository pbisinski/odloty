package git.pbisinski.odloty.api.repository

import git.pbisinski.odloty.api.datasource.SkyScannerDataSource
import git.pbisinski.odloty.api.entity.PlaceEntity
import git.pbisinski.odloty.api.model.PlaceModel
import io.reactivex.Single

interface LocalisationRepository {
  fun getPlaces(market: String, currency: String, locale: String, query: String): Single<List<PlaceModel>>
}

class LocalisationRepositoryImpl(
  private val dataSource: SkyScannerDataSource
) : LocalisationRepository {

  override fun getPlaces(market: String, currency: String, locale: String, query: String): Single<List<PlaceModel>> {
    return dataSource.getPlaces(market = market, currency = currency, locale = locale, query = query)
      .map { placesEntity ->
        placesEntity.places.map { placeEntity -> PlaceEntity.toModel(entity = placeEntity) }
      }
  }
}
