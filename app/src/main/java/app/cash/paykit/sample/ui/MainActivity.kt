/*
 * Copyright (C) 2023 Cash App
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
