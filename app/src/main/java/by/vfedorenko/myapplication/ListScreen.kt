package by.vfedorenko.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.vfedorenko.myapplication.databinding.ListFragmentBinding
import com.github.nitrico.lastadapter.LastAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Router


class ListFragment : Fragment() {

    companion object {
        private const val EXTRA_SELECTION = "EXTRA_SELECTION"

        fun newInstance(selection: Int) =
            ListFragment().apply {
                arguments = bundleOf(EXTRA_SELECTION to selection)
            }
    }

    private val selectionViewModel by sharedViewModel<SelectionViewModel>()
    private val viewModel by viewModel<ListViewModel>{
        parametersOf(
            selectionViewModel,
            arguments?.get(EXTRA_SELECTION)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ListFragmentBinding>(
            inflater,
            R.layout.list_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.recyclerView.run {
            adapter = LastAdapter(viewModel.data, BR.viewModel)
                .map<ListItemViewModel>(R.layout.item_selection)
                .into(this)

            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

            layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }
}

class ListViewModel(
    private val router: Router,
    private val selectionViewModel: SelectionViewModel,
    private val selection: Int
) : ViewModel() {

    val data = ObservableArrayList<ListItemViewModel>()

    private val onItemSelected: (String) -> Unit = {
        when(selection) {
            SELECTION_1 -> selectionViewModel.firstSelection.set(it)
            SELECTION_2 -> selectionViewModel.secondSelection.set(it)
        }
        router.exit()
    }

    init {
        val values = listOf(
            "Value 1",
            "Value 2",
            "Value 3",
            "Value 4",
            "Value 5",
            "Value 6"
        )
        data.addAll(
            values.map { ListItemViewModel(it, onItemSelected) }
        )
    }
}

class ListItemViewModel(val value: String, private val selectionCallback: (String) -> Unit) {
    fun onItemClick() {
        selectionCallback.invoke(value)
    }
}

