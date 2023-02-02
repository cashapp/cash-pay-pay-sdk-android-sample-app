package app.cash.paykit.sample.ui.ui.main

import androidx.lifecycle.ViewModel
import app.cash.paykit.sample.ui.ui.main.Screens.CHECKOUT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class Screens {
  ADD_TO_CART,
  CHECKOUT,
}
class MainViewModel : ViewModel() {

  private val _currentScreen = MutableStateFlow(Screens.ADD_TO_CART)
  val currentScreen: StateFlow<Screens> = _currentScreen

  fun navigateToCheckoutScreen() {
    _currentScreen.value = CHECKOUT
  }
}
