package git.pbisinski.odloty.view.screen.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import git.pbisinski.odloty.R
import git.pbisinski.odloty.databinding.FragmentPlaceChoosingBinding
import git.pbisinski.odloty.view.base.BaseBottomSheetFragment
import git.pbisinski.odloty.view.utils.DisposableVar

class PlaceChoosingFragment : BaseBottomSheetFragment() {

  override val bottomSheetLayout: View by lazy { binding.sheetRoot }

  private var binding: FragmentPlaceChoosingBinding by DisposableVar()

  override fun setupView(inflater: LayoutInflater, container: ViewGroup?): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_choosing, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.button1.setOnClickListener {
      (it as Button).text.toString().let(::dismiss)
    }
    binding.button2.setOnClickListener {
      (it as Button).text.toString().let(::dismiss)
    }
    binding.button3.setOnClickListener {
      (it as Button).text.toString().let(::dismiss)
    }
  }
}
