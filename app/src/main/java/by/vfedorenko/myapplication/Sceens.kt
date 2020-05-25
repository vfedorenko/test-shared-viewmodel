package by.vfedorenko.myapplication

import ru.terrakok.cicerone.android.support.SupportAppScreen

class ListScreen(private val selection: Int) : SupportAppScreen() {
    override fun getFragment() = ListFragment.newInstance(selection)
}
