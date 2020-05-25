package by.vfedorenko.myapplication

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class SelectionViewModel : ViewModel() {
    val firstSelection = ObservableField<String>()
    val secondSelection = ObservableField<String>()
}
