package git.pbisinski.odloty.view.utils

import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("isEditable")
fun EditText.setIsEditable(isEditable: Boolean) {
  inputType = if (isEditable) {
    InputType.TYPE_CLASS_TEXT
  } else {
    InputType.TYPE_NULL
  }
}
