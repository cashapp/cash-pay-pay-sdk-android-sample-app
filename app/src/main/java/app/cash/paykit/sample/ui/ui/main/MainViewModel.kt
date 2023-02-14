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

package app.cash.paykit.sample.ui.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paykit.core.CashAppPayKit
import app.cash.paykit.core.CashAppPayKitFactory
import app.cash.paykit.core.CashAppPayKitListener
import app.cash.paykit.core.PayKitState
import app.cash.paykit.core.PayKitState.ReadyToAuthorize
import app.cash.paykit.core.models.sdk.PayKitPaymentAction
import app.cash.paykit.sample.ui.ui.main.Screens.CHECKOUT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Screens {
  ADD_TO_CART,
  CHECKOUT,
}

/* Change these IDs and redirect URI accordingly for your own app. */
private const val sandboxClientID = "CAS-CI_PAYKIT_MOBILE_DEMO"
const val sandboxBrandID = "BRAND_9t4pg7c16v4lukc98bm9jxyse"

const val redirectURI = "cashpaykitsample://checkout"

class MainViewModel : ViewModel(), CashAppPayKitListener {

  // Used for simple navigation.
  private val _currentScreen = MutableStateFlow(Screens.ADD_TO_CART)
  val currentScreen: StateFlow<Screens> = _currentScreen

  // Cash PayKit State.
  private val _payKitState = MutableStateFlow<PayKitState>(PayKitState.NotStarted)
  val payKitState: StateFlow<PayKitState> = _payKitState.asStateFlow()

  private lateinit var payKitSdk: CashAppPayKit
  var currentRequestId: String? = null
    private set

  override fun payKitStateDidChange(newState: PayKitState) {
    // Store the `requestId`.
    if (newState is ReadyToAuthorize) {
      currentRequestId = newState.responseData.id
    }

    // Propagate state so consumers of this ViewModel can be notified of changes.
    _payKitState.value = newState
  }

  fun navigateToCheckoutScreen() {
    _currentScreen.value = CHECKOUT
  }

  fun initializeSDK() {
    payKitSdk = CashAppPayKitFactory.createSandbox(sandboxClientID)
    payKitSdk.registerForStateUpdates(this)
  }

  fun createOneTimePayment(paymentAction: PayKitPaymentAction) {
    viewModelScope.launch(Dispatchers.IO) {
      payKitSdk.createCustomerRequest(paymentAction)
    }
  }

  fun authorizeCustomerRequest(context: Context) {
    payKitSdk.authorizeCustomerRequest(context)
  }
}
