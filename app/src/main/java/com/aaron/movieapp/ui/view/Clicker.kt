package com.aaron.movieapp.ui.view

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aaron.movieapp.R
import kotlinx.coroutines.delay

@Composable
fun ClickerView() {
    /* ==============================
    ========== VARIABLES ==========
    ============================== */
    // Clicker
    var coin by remember { mutableDoubleStateOf(0.0) }
    var clickValue by rememberSaveable { mutableDoubleStateOf(1.0) }
    var upgradePrice by rememberSaveable { mutableDoubleStateOf(10.0) }
    val enoughCoin = enoughCoin(coin, upgradePrice)
    var isClicked by rememberSaveable { mutableStateOf(false) }

    // Upgrade Multiplier
    val clickUpgradeMultiplier = 1.5
    val priceUpgradeMultiplier = 2

    // Utils
    val twoPoints = DecimalFormat("#.##")

    /* ==============================
    ========== UI LAYOUT ==========
    ============================== */
    // STYLES
    val headerStyle = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
    )

    val header2Style = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
    )

    val header3Style = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
    )

    val normalStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        textAlign = TextAlign.Center,

        )


    // COLORS
    val green = Color(0xFF12D708)
    val gray = Color(0xFFA2A2A2)
    val grayTransparent = Color(0x6DC5C5C5)


    /* FLOW
    1. Screen is same
       - box, column{ box{ column{ text, text, text}},
         column{ text, image, text},
         box {column{text, text, box{text }}}
         }
       - Cat clickable, coin += clickValue
       - Button clickable, enabled = enoughCoin, if clicked:
       --- coin -= upgradePrice, clickValue *= clickUpgradeMultiplier,
           upgradePrice *= priceUpgradeMultiplier
     */


    // LAYOUT
    // Background
    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background
        Image (
            painter = painterResource(R.drawable.clickerbg),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.5f),
                blendMode = BlendMode.Multiply
            )
        )

        // Layout
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 64.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 48.dp, horizontal = 36.dp)

        ) {
            /* FLOW
            1. Screen is same
               - box, column { !!! ------------------------
                 box{ column{ text, text, text}},
                 column{ text, image, text},
                 box {column{text, text, box{text }}}
                 }
               - Cat clickable, coin += clickValue
               - Button clickable, enabled = enoughCoin, if clicked:
               --- coin -= upgradePrice, clickValue *= clickUpgradeMultiplier,
                   upgradePrice *= priceUpgradeMultiplier
             */

            // Top Box
            Box (
                modifier = Modifier
                    .background(
                        color = grayTransparent,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column (
                  modifier = Modifier 
                      .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    Text (
                        text = "Your Coins",
                        style = header2Style
                    )

                    Text (
                        text = "${coin.toLong()}",
                        style = headerStyle,
                        color = green
                    )

                    Text (
                        text = "${twoPoints.format(clickValue)} coins per tap",
                        style = normalStyle
                    )
                }
            }

            // Tap the cat column
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text (
                    text = "Tap the Cat!",
                    style = header3Style
                )

                Image (
                    painter = if (isClicked) painterResource(R.drawable.clicked)
                              else painterResource(R.drawable.unclicked),
                    contentDescription = "Car",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            enabled = true,
                            onClick = {
                                isClicked = true
                                coin += clickValue
                            }
                        )
                )

                Text (
                    text = if(isClicked) "Meow!"
                           else "Purr~",
                    style = normalStyle
                )
            }

            // Upgrade Box
            Box (
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    Text (
                        text = "Give Me Your Coin",
                        style = header2Style,
                        color = Color.Black
                    )

                    Text (
                        text = "Next upgrade: +${twoPoints.format((clickValue * clickUpgradeMultiplier) - clickValue)} coins per tap",
                        style = normalStyle,
                        color = Color.Black
                    )

                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (enoughCoin) green
                                        else gray,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable(
                                enabled = enoughCoin,
                                onClick = {
                                    coin -= upgradePrice
                                    clickValue *= clickUpgradeMultiplier
                                    upgradePrice *= priceUpgradeMultiplier
                                }
                            )
                    ) {
                        Text (
                            text = if (enoughCoin) "Pay for ${upgradePrice.toLong()} coins"
                                   else "Find ${(upgradePrice - coin).toLong()} more coins",
                            style = normalStyle,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                    }

                }
            }

        }
    }



    // Cat animation
    LaunchedEffect(isClicked) {
        delay(5L)
        isClicked = false
    }
}


@Preview (showSystemUi = true, showBackground = true)
@Composable
fun ClickerPreview() {
    ClickerView()
}



/* ==============================
========== FUNCTIONS ==========
============================== */

fun enoughCoin(coin: Double, upgradePrice: Double): Boolean {
    return coin >= upgradePrice
}

