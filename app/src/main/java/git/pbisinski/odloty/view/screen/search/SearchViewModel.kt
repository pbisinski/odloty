package git.pbisinski.odloty.view.screen.search

import git.pbisinski.odloty.api.model.PlaceModel
import git.pbisinski.odloty.api.repository.LocationRepository
import git.pbisinski.odloty.view.base.BaseViewModel
import git.pbisinski.odloty.view.screen.splash.SplashScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge

data class SearchState(
  val text: String
)

@ExperimentalCoroutinesApi
class SearchViewModel(
  private val locationRepository: LocationRepository
) : BaseViewModel<SearchIntent, SearchState, SearchEvent>() {

  override val initial: SearchState
    get() = SearchState(text = "korutyna :)")

  @FlowPreview
  override fun Flow<SearchIntent>.toChange(): Flow<SearchState.() -> SearchState> {
    val places = getPlaces()
      .map {
        { state: SearchState -> state.copy(text = "Pobrano ${it.size} elementów") }
      }

    return merge(
      filterIsInstance<SearchIntent.Download>().flatMapMerge { places },
    )
  }

  override fun Flow<SearchIntent>.toEvent(): Flow<SearchEvent> {
    val navigation = filterIsInstance<SearchIntent.Route>()
      .map { routeRequest ->
        when (routeRequest) {
          SearchIntent.Route.GoToSplash -> SearchEvent.RouteTo(destination = SplashScreen)
        }
      }

    return merge(navigation)
  }

  private fun getPlaces(): Flow<List<PlaceModel>> = suspendedFlow {
    locationRepository.getPlaces(market = "PL", currency = "ILS", locale = "en-EN", query = "Israel")
  }
}
