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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paykit.sample.R
import app.cash.paykit.sample.ui.theme.AppTextStyles
import app.cash.paykit.sample.ui.theme.PayKitSampleTheme

@Composable
fun AddToCartScreen(
  onAddToCart: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 28.dp, vertical = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      painter = painterResource(id = R.drawable.cup_coffee),
      contentDescription = null,
      modifier = Modifier.height(375.dp),
    )

    Spacer(modifier = Modifier.height(28.dp))

    Text(
      text = stringResource(R.string.coffee_title),
      style = AppTextStyles.TitleText4,
    )

    Text(
      text = stringResource(R.string.coffee_price),
      style = AppTextStyles.SubtitleText1,
    )

    Spacer(modifier = Modifier.height(6.dp))

    Text(
      text = stringResource(R.string.coffee_description),
      style = AppTextStyles.BodyText1,
      textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.weight(1f))

    Button(
      onClick = onAddToCart,
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp),
      shape = RoundedCornerShape(32.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = Color.Black,
        contentColor = Color.White,
      ),
    ) {
      Text(
        text = stringResource(R.string.add_to_cart),
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun AddToCartScreenPreview() {
  PayKitSampleTheme {
    AddToCartScreen(onAddToCart = {})
  }
}
