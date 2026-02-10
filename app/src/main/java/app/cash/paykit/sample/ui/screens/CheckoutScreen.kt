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

package app.cash.paykit.sample.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paykit.core.CashAppPayState.Approved
import app.cash.paykit.core.CashAppPayState.CashAppPayExceptionState
import app.cash.paykit.core.CashAppPayState.Declined
import app.cash.paykit.core.CashAppPayState.ReadyToAuthorize
import app.cash.paykit.core.models.sdk.CashAppPayCurrency.USD
import app.cash.paykit.core.models.sdk.CashAppPayPaymentAction.OneTimeAction
import app.cash.paykit.sample.R
import app.cash.paykit.sample.ui.MainViewModel
import app.cash.paykit.sample.ui.SANDBOX_BRAND_ID
import app.cash.paykit.sample.ui.theme.AppTextStyles
import app.cash.paykit.sample.ui.theme.PayKitSampleTheme
import app.cash.paykit.sample.ui.theme.TextPrimary
import app.cash.paykit.sample.ui.theme.TextSectionTitle
import app.cash.paykit.ui.compose.CashAppPayButton

@Composable
fun CheckoutScreen(
  viewModel: MainViewModel,
  snackbarHostState: SnackbarHostState,
) {
  val payKitState by viewModel.payKitState.collectAsStateWithLifecycle()

  val approvedMessage = stringResource(R.string.state_approved)
  val declinedMessage = stringResource(R.string.state_declined)
  val errorMessage = stringResource(R.string.state_error)

  LaunchedEffect(Unit) {
    viewModel.initializeSDK()
    val oneTimePayment = OneTimeAction(USD, 825, SANDBOX_BRAND_ID)
    viewModel.createOneTimePayment(oneTimePayment)
  }

  LaunchedEffect(payKitState) {
    when (val state = payKitState) {
      is Approved -> snackbarHostState.showSnackbar(approvedMessage)

      is Declined -> snackbarHostState.showSnackbar(declinedMessage)

      is CashAppPayExceptionState -> {
        Log.e("Sample", "Exception occurred: ${state.exception}")
        snackbarHostState.showSnackbar(errorMessage)
      }

      else -> { }
    }
  }

  val isButtonEnabled = payKitState is ReadyToAuthorize

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState()),
  ) {
    OrderSummarySection()

    Spacer(modifier = Modifier.height(24.dp))

    ContactDetailsSection(
      modifier = Modifier.padding(horizontal = 28.dp),
    )

    Spacer(modifier = Modifier.height(16.dp))

    PaymentSection(
      modifier = Modifier.padding(horizontal = 28.dp),
      isButtonEnabled = isButtonEnabled,
      onPayClick = { viewModel.authorizeCustomerRequest() },
    )

    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Composable
private fun OrderSummarySection() {
  Column(
    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
  ) {
    Text(
      text = stringResource(R.string.order_summary_title).uppercase(),
      style = AppTextStyles.TitleText4.copy(color = TextSectionTitle),
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
      Image(
        painter = painterResource(R.drawable.coffee_thumb),
        contentDescription = null,
        modifier = Modifier.size(52.dp),
      )

      Spacer(modifier = Modifier.width(5.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = stringResource(R.string.coffee_label),
          style = AppTextStyles.SubtitleText2,
        )
        Text(
          text = stringResource(R.string.small_8oz_label),
          style = AppTextStyles.CaptionText,
        )
      }

      Text(
        text = stringResource(R.string.coffee_price),
        style = AppTextStyles.BodyText2,
      )
    }

    Spacer(modifier = Modifier.height(19.dp))

    PriceRow(
      label = stringResource(R.string.subtotal_label),
      price = stringResource(R.string.coffee_price),
      labelStyle = AppTextStyles.BodyText3,
      priceStyle = AppTextStyles.BodyText4,
    )

    Spacer(modifier = Modifier.height(8.dp))

    PriceRow(
      label = stringResource(R.string.taxes_label),
      price = stringResource(R.string.taxes_price),
      labelStyle = AppTextStyles.BodyText3,
      priceStyle = AppTextStyles.BodyText4,
    )

    Spacer(modifier = Modifier.height(32.dp))

    PriceRow(
      label = stringResource(R.string.total_label),
      price = stringResource(R.string.total_price),
      labelStyle = AppTextStyles.SubtitleText2.copy(color = TextPrimary),
      priceStyle = AppTextStyles.BodyText2,
    )
  }
}

@Composable
private fun PriceRow(
  label: String,
  price: String,
  labelStyle: TextStyle,
  priceStyle: TextStyle,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Text(text = label, style = labelStyle)
    Text(text = price, style = priceStyle)
  }
}

@Composable
private fun ContactDetailsSection(modifier: Modifier = Modifier) {
  var email by remember { mutableStateOf("") }
  var name by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }

  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
    color = Color.White,
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
    ) {
      Text(
        text = stringResource(R.string.contact_title).uppercase(),
        style = AppTextStyles.TitleText4.copy(color = TextSectionTitle),
      )

      Spacer(modifier = Modifier.height(12.dp))

      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(stringResource(R.string.email_hint)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text(stringResource(R.string.name_hint)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text(stringResource(R.string.phone_hint)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
      )

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = stringResource(R.string.contact_terms),
        style = AppTextStyles.CaptionText,
      )
    }
  }
}

@Composable
private fun PaymentSection(
  modifier: Modifier = Modifier,
  isButtonEnabled: Boolean,
  onPayClick: () -> Unit,
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
    color = Color.White,
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
    ) {
      Text(
        text = stringResource(R.string.payment_title).uppercase(),
        style = AppTextStyles.TitleText4.copy(color = TextSectionTitle),
      )

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = stringResource(R.string.encryption_notice),
        style = AppTextStyles.BodyText3,
      )

      Spacer(modifier = Modifier.height(16.dp))

      CashAppPayButton(
        enabled = isButtonEnabled,
        onClick = onPayClick,
      )
    }
  }
}

@Preview(showBackground = true, backgroundColor = PREVIEW_BACKGROUND_COLOR)
@Composable
private fun OrderSummarySectionPreview() {
  PayKitSampleTheme {
    OrderSummarySection()
  }
}

@Preview(showBackground = true, backgroundColor = PREVIEW_BACKGROUND_COLOR)
@Composable
private fun ContactDetailsSectionPreview() {
  PayKitSampleTheme {
    ContactDetailsSection(modifier = Modifier.padding(horizontal = 28.dp))
  }
}

@Preview(showBackground = true, backgroundColor = PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PaymentSectionPreview() {
  PayKitSampleTheme {
    PaymentSection(
      modifier = Modifier.padding(horizontal = 28.dp),
      isButtonEnabled = true,
      onPayClick = {},
    )
  }
}

@Preview(showBackground = true, backgroundColor = PREVIEW_BACKGROUND_COLOR)
@Composable
private fun CheckoutScreenContentPreview() {
  PayKitSampleTheme {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
    ) {
      OrderSummarySection()
      Spacer(modifier = Modifier.height(24.dp))
      ContactDetailsSection(modifier = Modifier.padding(horizontal = 28.dp))
      Spacer(modifier = Modifier.height(16.dp))
      PaymentSection(
        modifier = Modifier.padding(horizontal = 28.dp),
        isButtonEnabled = true,
        onPayClick = {},
      )
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

private const val PREVIEW_BACKGROUND_COLOR = 0xFFF6F6F6
