package git.pbisinski.odloty.view.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> LiveData<T>.skip(count: Int): LiveData<T> {
  val mediatorLiveData = MediatorLiveData<T>()
  var skippedCount = 0
  mediatorLiveData.addSource(this) { data ->
    if (skippedCount >= count) {
      mediatorLiveData.value = data
    }
    skippedCount++
  }
  return mediatorLiveData
}

fun <T> LiveData<T>.skipInitial(): LiveData<T> = skip(count = 1)
