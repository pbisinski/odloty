package git.pbisinski.odloty.view.screen.search

import android.util.Log
import git.pbisinski.odloty.api.repository.LocationRepository
import git.pbisinski.odloty.view.base.BaseViewModel
import git.pbisinski.odloty.view.screen.bottomsheet.PlaceChoosingScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

private typealias Change = SearchState.() -> SearchState

data class SearchState(
  val text: String
)

@ExperimentalCoroutinesApi
class SearchViewModel(
  private val locationRepository: LocationRepository
) : BaseViewModel<SearchIntent, SearchState, SearchEvent>() {

  override val initial: SearchState
    get() = SearchState(text = "korutyna :)")

  override fun Flow<SearchIntent>.toChange(): Flow<Change> = merge(
    filterIsInstance<SearchIntent.Download>().flatMapLatest { getPlaces(it.query) }
  )

  override fun Flow<SearchIntent>.toEvent(): Flow<SearchEvent> = merge(
    filterIsInstance<SearchIntent.Route>().map { event -> onRouteEvent(event) }
  )

  private fun getPlaces(query: String): Flow<Change> = suspendedFlow {
    locationRepository.getPlaces(market = "PL", currency = "PLN", locale = "en-EN", query = query)
  }.map { places ->
    val first = places.firstOrNull()
    val updateText = "Downloaded ${places.size} elements\nFirst ${first?.placeName}"
    return@map { state: SearchState -> state.copy(text = updateText) }
  }.catch { error ->
    Log.e(devTag, "places failed", error)
  }

  private fun onRouteEvent(event: SearchIntent.Route) =
    when (event) {
      SearchIntent.Route.GoToSplash -> SearchEvent.RouteTo(destination = PlaceChoosingScreen)
    }
}
