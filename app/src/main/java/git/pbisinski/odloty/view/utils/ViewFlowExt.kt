package git.pbisinski.odloty.view.utils

import android.view.View
import git.pbisinski.odloty.view.BottomNavigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun View.clicks(): Flow<Unit> = callbackFlow {
  setOnClickListener { offer(Unit) }
  awaitClose { setOnClickListener(null) }
}

@ExperimentalCoroutinesApi
fun BottomNavigator.screenChanges() = callbackFlow {
  onNavigation = { screen -> offer(screen) }
  awaitClose {
    // NO-OP
  }
}
