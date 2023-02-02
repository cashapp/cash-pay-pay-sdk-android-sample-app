package app.cash.paykit.sample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.cash.paykit.sample.R
import app.cash.paykit.sample.ui.ui.main.AddToCartFragment
import app.cash.paykit.sample.ui.ui.main.CheckoutFragment
import app.cash.paykit.sample.ui.ui.main.MainViewModel
import app.cash.paykit.sample.ui.ui.main.Screens
import app.cash.paykit.sample.ui.ui.main.Screens.ADD_TO_CART
import app.cash.paykit.sample.ui.ui.main.Screens.CHECKOUT
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Start a coroutine in the lifecycle scope
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.currentScreen.collect { currentScreen ->
          navigateToScreen(currentScreen)
        }
      }
    }
  }

  private fun navigateToScreen(screen: Screens) {
    val fragment = when (screen) {
      ADD_TO_CART -> AddToCartFragment.newInstance()
      CHECKOUT -> CheckoutFragment.newInstance()
    }
    supportFragmentManager.beginTransaction()
      .replace(R.id.container, fragment)
      .commitNow()
  }
}
