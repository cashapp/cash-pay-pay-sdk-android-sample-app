/*
 * Copyright (C) 2026 Cash App
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
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paykit.sample.R
import app.cash.paykit.sample.ui.screens.AddToCartScreen
import app.cash.paykit.sample.ui.screens.CheckoutScreen
import app.cash.paykit.sample.ui.theme.BackgroundGray
import app.cash.paykit.sample.ui.theme.PayKitSampleTheme

class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      PayKitSampleTheme {
        val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
          topBar = { AppTopBar() },
          snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
          Box(modifier = Modifier.padding(paddingValues)) {
            when (currentScreen) {
              Screens.ADD_TO_CART -> AddToCartScreen(
                onAddToCart = { viewModel.navigateToCheckoutScreen() },
              )

              Screens.CHECKOUT -> CheckoutScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
              )
            }
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar() {
  TopAppBar(
    title = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Image(
          painter = painterResource(R.drawable.gp_icon),
          contentDescription = null,
          modifier = Modifier.padding(start = 16.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Image(
          painter = painterResource(R.drawable.gpc_title),
          contentDescription = null,
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = BackgroundGray,
    ),
  )
}
