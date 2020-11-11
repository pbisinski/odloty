package git.pbisinski.odloty.api.repository

import git.pbisinski.odloty.api.datasource.SkyScannerDataSource
import git.pbisinski.odloty.api.entity.PlacesEntity
import git.pbisinski.odloty.api.model.PlaceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface LocationRepository {
  suspend fun getPlaces(market: String, currency: String, locale: String, query: String): List<PlaceModel>
}

class LocationRepositoryImpl(
  private val dataSource: SkyScannerDataSource
) : LocationRepository {

  override suspend fun getPlaces(market: String, currency: String, locale: String, query: String): List<PlaceModel> {
    return withContext(Dispatchers.IO) {
      dataSource
        .getPlaces(market = market, currency = currency, locale = locale, query = query)
        .let(PlacesEntity.Mapper::toModel)
        .places
    }
  }
}
