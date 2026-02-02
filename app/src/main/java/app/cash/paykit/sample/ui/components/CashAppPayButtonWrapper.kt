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

package app.cash.paykit.sample.ui.components

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.cash.paykit.core.ui.CashAppPayButton
import app.cash.paykit.sample.R
import app.cash.paykit.sample.ui.theme.PayKitSampleTheme

@SuppressLint("InflateParams")
@Composable
fun CashAppPayButtonWrapper(
  enabled: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  AndroidView(
    modifier = modifier
      .fillMaxWidth()
      .height(52.dp),
    factory = { context ->
      // Inflate the button from XML to get proper styling from the PayKit SDK
      LayoutInflater.from(context)
        .inflate(R.layout.cash_app_pay_button, null) as CashAppPayButton
    },
    update = { button ->
      button.isEnabled = enabled
      button.setOnClickListener { onClick() }
    },
  )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CashAppPayButtonWrapperEnabledPreview() {
  PayKitSampleTheme {
    CashAppPayButtonWrapper(
      enabled = true,
      onClick = {},
      modifier = Modifier.padding(16.dp),
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CashAppPayButtonWrapperDisabledPreview() {
  PayKitSampleTheme {
    CashAppPayButtonWrapper(
      enabled = false,
      onClick = {},
      modifier = Modifier.padding(16.dp),
    )
  }
}
