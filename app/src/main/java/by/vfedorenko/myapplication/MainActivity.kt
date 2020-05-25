package by.vfedorenko.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    private val navigationHolder by inject<NavigatorHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(SupportAppNavigator(this, R.id.container))
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}
