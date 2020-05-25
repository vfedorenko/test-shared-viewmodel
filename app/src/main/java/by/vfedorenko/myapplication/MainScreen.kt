package by.vfedorenko.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import by.vfedorenko.myapplication.databinding.MainFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Router

const val SELECTION_1 = 1
const val SELECTION_2 = 2

class MainFragment : Fragment() {

    private val selectionViewModel by sharedViewModel<SelectionViewModel>()
    private val viewModel by viewModel<MainViewModel>{
        parametersOf(selectionViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<MainFragmentBinding>(
            inflater,
            R.layout.main_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }
}

class MainViewModel(
    private val router: Router,
    val selection: SelectionViewModel
) : ViewModel() {

    val finalValue = ObservableField<String>()

    fun onFirstClick() {
        router.navigateTo(ListScreen(SELECTION_1))
    }

    fun onSecondClick() {
        router.navigateTo(ListScreen(SELECTION_2))
    }

    fun onCtaClick() {
        finalValue.set("${selection.firstSelection.get()} : ${selection.secondSelection.get()}")
    }
}
