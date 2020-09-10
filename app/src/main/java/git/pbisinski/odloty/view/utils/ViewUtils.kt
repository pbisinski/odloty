package git.pbisinski.odloty.view.utils

import android.view.View
import android.view.ViewGroup

inline fun <reified T : View> ViewGroup.getChildViews(): ArrayList<T> {
  val childViews = ArrayList<T>()
  for (i in 0 until childCount) {
    val child = getChildAt(i) as? T ?: continue
    childViews.add(child)
  }
  return childViews
}
