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

import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.cash.paykit.sample.R
import app.cash.paykit.sample.ui.theme.AppTextStyles
import app.cash.paykit.sample.ui.theme.PayKitSampleTheme
import app.cash.paykit.ui.compose.CashAppPayButton
import app.cash.paykit.ui.compose.CashAppPayButtonLabel
import app.cash.paykit.ui.compose.CashAppPayButtonStyle
import app.cash.paykit.ui.compose.CashAppPaymentMethod
import app.cash.paykit.ui.compose.CashAppPaymentMethodSize
import app.cash.paykit.ui.views.CashAppPayButton as CashAppPayButtonView
import app.cash.paykit.ui.views.CashAppPayButtonLabel as CashAppPayButtonViewLabel

/**
 * Every Pay Kit UI component, label, and style, in both Compose and Views.
 */
@Composable
fun ComponentsScreen() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 28.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    SectionTitle(stringResource(R.string.components_compose_labels))
    CashAppPayButtonLabel.entries.forEach { label ->
      CashAppPayButton(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        label = label,
      )
    }

    SectionTitle(stringResource(R.string.components_compose_styles))
    CashAppPayButtonStyle.entries.forEach { style ->
      Caption(style.name)
      CashAppPayButton(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        style = style,
      )
    }
    Caption(stringResource(R.string.components_disabled))
    CashAppPayButton(
      onClick = {},
      modifier = Modifier.fillMaxWidth(),
      enabled = false,
    )

    SectionTitle(stringResource(R.string.components_compose_payment_method))
    val cashtag = stringResource(R.string.sample_cashtag)
    CashAppPaymentMethod(cashtag = cashtag)
    CashAppPaymentMethod(cashtag = cashtag, size = CashAppPaymentMethodSize.Small)
    CashAppPaymentMethod(cashtag = cashtag, usePolychromeAsset = true)

    AndroidView(
      factory = { context ->
        LayoutInflater.from(context).inflate(R.layout.views_components, null).apply {
          findViewById<CashAppPayButtonView>(R.id.label_in_code_button).label = CashAppPayButtonViewLabel.Pay
          findViewById<View>(R.id.disabled_button).isEnabled = false
        }
      },
      modifier = Modifier.fillMaxWidth(),
    )
  }
}

@Composable
private fun SectionTitle(text: String) {
  Text(
    text = text,
    style = AppTextStyles.TitleText4,
    modifier = Modifier.padding(top = 16.dp),
  )
}

@Composable
private fun Caption(text: String) {
  Text(text = text, style = AppTextStyles.CaptionText)
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
private fun ComponentsScreenPreview() {
  PayKitSampleTheme {
    ComponentsScreen()
  }
}
