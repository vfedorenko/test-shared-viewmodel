package by.vfedorenko.myapplication

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val navigationModule = module {
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
    single { Cicerone.create() }
}

val viewModelsModule = module {
    viewModel { (selectionViewModel: SelectionViewModel) ->
        MainViewModel(
            router = get(),
            selection = selectionViewModel
        )
    }

    viewModel { (selectionViewModel: SelectionViewModel, selection: Int) ->
        ListViewModel(
            router = get(),
            selectionViewModel = selectionViewModel,
            selection = selection
        )
    }

    viewModel { SelectionViewModel() }
}
