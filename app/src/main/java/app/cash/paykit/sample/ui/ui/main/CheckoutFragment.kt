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

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.cash.paykit.core.PayKitState.Approved
import app.cash.paykit.core.PayKitState.Authorizing
import app.cash.paykit.core.PayKitState.CreatingCustomerRequest
import app.cash.paykit.core.PayKitState.Declined
import app.cash.paykit.core.PayKitState.NotStarted
import app.cash.paykit.core.PayKitState.PayKitExceptionState
import app.cash.paykit.core.PayKitState.PollingTransactionStatus
import app.cash.paykit.core.PayKitState.ReadyToAuthorize
import app.cash.paykit.core.PayKitState.RetrievingExistingCustomerRequest
import app.cash.paykit.core.PayKitState.UpdatingCustomerRequest
import app.cash.paykit.core.models.sdk.PayKitCurrency.USD
import app.cash.paykit.core.models.sdk.PayKitPaymentAction.OneTimeAction
import app.cash.paykit.sample.R
import app.cash.paykit.sample.databinding.FragmentCheckoutBinding
import kotlinx.coroutines.launch

class CheckoutFragment : Fragment() {

  companion object {
    fun newInstance() = CheckoutFragment()
  }

  private var _binding: FragmentCheckoutBinding? = null
  private val binding get() = _binding!!
  private val viewModel: MainViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.initializeSDK()
    val oneTimePayment = OneTimeAction(redirectURI, USD, 825, sandboxBrandID)
    viewModel.createOneTimePayment(oneTimePayment)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    handlePayKitStateChanges()
    binding.payKitButton.setOnClickListener {
      viewModel.authorizeCustomerRequest(requireContext())
    }
  }

  private fun handlePayKitStateChanges() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.payKitState.collect { newState ->
          when (newState) {
            is Approved -> {
              // Use `newState.responseData` to retrieve the information necessary to send to your own backend.
              showToast(R.string.state_approved)
            }

            Authorizing -> {
              // Use this state to display loading status if desired.
            }

            CreatingCustomerRequest -> {
              // Use this state to display loading status if desired.
            }

            Declined -> {
              showToast(R.string.state_declined)
            }

            NotStarted -> {
              binding.payKitButton.isEnabled = false
            }

            is PayKitExceptionState -> {
              showToast(R.string.state_error)
              Log.e("Sample", "Exception occurred: ${newState.exception}")
            }
            PollingTransactionStatus -> {
              // Use this state to display loading status if desired.
            }

            is ReadyToAuthorize -> {
              binding.payKitButton.isEnabled = true
            }

            UpdatingCustomerRequest -> {
              // Use this state to display loading status if desired.
            }

            RetrievingExistingCustomerRequest -> {
              // Use this state to display loading status if desired.
              // Used only when retrieving existing customer request. Corner case (eg.: app killed)
            }
          }
        }
      }
    }
  }

  private fun showToast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
  }
}
