package com.aaron.movieapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
fun ReactionView() {
    /* ==============================
    ========== VARIABLES ==========
    ============================== */
    // Trial
    val trialAttempts = 3
    var trialResults by rememberSaveable { mutableStateOf(ArrayList<Long>())}
    var currentTrial by rememberSaveable { mutableIntStateOf(0) }
    var trialAverage: Long

    // Screen
    var screen by rememberSaveable { mutableStateOf("StartScreen") }
    /*
    StartScreen
    GetReadyScreen
    ClickNowScreen
    TrialFinishedScreen
    TrialFailedScreen
    EndScreen
    StartAfterTrialScreen
     */

    // Interactable
    var startTime by rememberSaveable { mutableLongStateOf(0L) }
    var reactionTime by rememberSaveable { mutableLongStateOf(0L) }

    /* ==============================
    ========== UI LAYOUT ==========
    ============================== */
    // STYLES
    val headerStyle = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
    )

    val header2Style = TextStyle(
        fontSize = 24.sp,
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
    val lightBlue = Color(0xFF68D5E5)
    val green = Color(0xFF12D708)
    val red = Color(0xFFFF4D4D)
    val gray = Color(0xFFCBCBCB)
    val finalRed = Color(0xFFFF5509)
    val finalOrange = Color(0xFFFF950F)
    val finalBlue = Color(0xFF00A4FF)
    val finalGreen = Color(0xFF00FF7B)


    /* FLOW
    1. StartScreen
       - Blue bg, horizontal center, vertical center
       - Text, icon, text, text
       - Click, go to GetReadyScreen

    2. GetReadyScreen
       - Gray bg, horizontal center, vertical center
       - Text, icon, text, text
       - Click, go to TrialFailedScreen
       --- Don't add currentTrial

    3. ClickNowScreen
       - Green bg, horizontal center, vertical center
       - Text, icon, text, text
       - Click, go to TrialFinishedScreen
       --- Add currentTrial
       --- reactionTime = now - startTime
       --- reactionTime add to trialResults

    4. TrialFinishedScreen
       - Green bg, horizontal center, vertical center
       - Text, icon, text, text, box {
             text, row { column {text, text}, column {text, text}, column {text, text} }
         }
       - Click, go to GetReadyScreen

    5. TrialFailedScreen
       - Red bg, horizontal center, vertical center
       - Text, icon, text, text, box {
             text, row { column {text, text}, column {text, text}, column {text, text} }
         }
       - Click, go to GetReadyScreen

    6. EndScreen
       - <when> bg, horizontal center, vertical center
       - Text, <when> image, text, text, box {
             text, row { column {text, text}, column {text, text}, column {text, text} }
         }

     7. StartAfterTrialScreen
       - Blue bg, horizontal center, vertical center
       - Text, icon, text, text, box {
             text, row { column {text, text}, column {text, text}, column {text, text} }
         }
       - Click, go to StartAfterTrialScreen
     */

    LaunchedEffect(screen) {
        when (screen) {
            "GetReadyScreen" -> {
                delay((500..4500).random().toLong())
                startTime = System.currentTimeMillis()
                screen = "ClickNowScreen"
            }
        }
    }


    // LAYOUT
    // Background
    when (screen) {
        "StartScreen" -> {
            // Reset array
            var i = 0
            while(!trialResults.isNullOrEmpty()) {
                trialResults.removeAt(i)
            }

            currentTrial = 0

            Box (
                modifier = Modifier.background(
                    color = lightBlue
                )
                    .clickable(
                        enabled = true,
                        onClick = {
                            screen = "GetReadyScreen"
                        }
                    )
            ) {

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    Text(
                        text = "Reaction",
                        style = headerStyle
                    )

                    Icon(
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = "Get Ready",
                        tint = Color.White,
                        modifier = Modifier
                            .size(120.dp)
                    )

                    Text(
                        text = "Test",
                        style = normalStyle
                    )

                    Text(
                        text = "Click to start",
                        style = normalStyle
                    )

                }
            }
        }

        "GetReadyScreen" -> {
            Box (
                modifier = Modifier.background(
                    color = gray
                )
                    .clickable(
                        enabled = true,
                        onClick = {
                            screen = "TrialFailedScreen"
                        }
                    )
            ) {

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    Text (
                        text = "Get Ready",
                        style = headerStyle
                    )

                    Icon (
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Get Ready",
                        tint = Color.White,
                        modifier = Modifier
                            .size(120.dp)
                    )

                    Text (
                        text = "Wait for green light...",
                        style = normalStyle
                    )

                    Text (
                        text = "DON'T CLICK YET",
                        style = normalStyle
                    )

                }
            }
        }

        "ClickNowScreen" -> {
            Box (
                modifier = Modifier.background(
                    color = green
                )
                    .clickable(
                        enabled = true,
                        onClick = {
                            screen = "TrialFinishedScreen"

                            // Trial handling
                            reactionTime = System.currentTimeMillis() - startTime
                            trialResults.add(reactionTime)
                            currentTrial++
                        }
                    )
            ) {

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    Text (
                        text = "GO!",
                        style = headerStyle
                    )

                    Icon (
                        imageVector = Icons.AutoMirrored.Filled.DirectionsRun,
                        contentDescription = "CLICK NOW!",
                        tint = Color.White,
                        modifier = Modifier
                            .size(120.dp)
                    )

                    Text (
                        text = "CLICK NOW!",
                        style = normalStyle
                    )

                    Text (
                        text = "TAP AS FAST AS YOU CAN!",
                        style = normalStyle
                    )

                }
            }
        }

        "TrialFinishedScreen" -> {
            Box (
                modifier = Modifier.background(
                    color = green
                )
                    .clickable(
                        enabled = true,
                        onClick = {
                            screen = if (currentTrial <  trialAttempts) {
                                "StartAfterTrialScreen"
                            } else {
                                "EndScreen"
                            }
                        }
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    Text (
                        text = "Trial $currentTrial Complete!",
                        style = headerStyle
                    )

                    Icon (
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Complete",
                        tint = Color.White,
                        modifier = Modifier
                            .size(120.dp)
                    )

                    Text (
                        text = "Time: ${reactionTime}ms",
                        style = normalStyle
                    )

                    Text (
                        text = if (currentTrial < trialAttempts) "Continue to Trial ${currentTrial + 1}"
                            else "Continue to results",
                        style = normalStyle
                    )

                    Box (
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .shadow(
                                elevation = 15.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = Color.Black,
                                spotColor = Color.Black
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            ),
                    ) {

                        Column (
                            modifier = Modifier
                                .padding(all = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text (
                                text = "Trial Results",
                                color = finalBlue,
                                style = header2Style
                            )

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                var counter = 0
                                for (trialTime in trialResults) {
                                    Column (
                                        //modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text (
                                            text = "${counter + 1}",
                                            style = header3Style,
                                            color = finalGreen
                                        )

                                        Text (
                                            text = "${trialTime}ms",
                                            style = normalStyle,
                                            color = Color.Black
                                        )

                                        counter++
                                    }
                                }
                            }
                        }
                    }



                }
            }
        }

        "TrialFailedScreen" -> {
            Box (
                modifier = Modifier.background(
                    color = red
                )
                    .clickable(
                        enabled = true,
                        onClick = {
                            screen = "StartAfterTrialScreen"
                        }
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    Text (
                        text = "FAIL!",
                        style = headerStyle
                    )

                    Icon (
                        imageVector = Icons.Default.ThumbDown,
                        contentDescription = "Fail",
                        tint = Color.White,
                        modifier = Modifier
                            .size(120.dp)
                    )

                    Text (
                        text = "You clicked too early. TRY TO READ THE RULE BRO",
                        style = normalStyle
                    )

                    Text (
                        text = "TRY AGAIN",
                        style = normalStyle
                    )

                    Box (
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .shadow(
                                elevation = 15.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = Color.Black,
                                spotColor = Color.Black
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            ),
                    ) {

                        Column (
                            modifier = Modifier
                                .padding(all = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text (
                                text = "Trial Results",
                                color = finalBlue,
                                style = header2Style
                            )

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                var counter = 0
                                for (trialTime in trialResults) {
                                    Column (
                                        //modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text (
                                            text = "${counter + 1}",
                                            style = header3Style,
                                            color = finalGreen
                                        )

                                        Text (
                                            text = "${trialTime}ms",
                                            style = normalStyle,
                                            color = Color.Black
                                        )

                                        counter++
                                    }
                                }
                            }
                        }
                    }



                }
            }
        }

        "StartAfterTrialScreen" -> {
            Box (
                modifier = Modifier.background(
                    color = lightBlue
                )
                    .clickable(
                        enabled = true,
                        onClick = {
                            screen = "GetReadyScreen"
                        }
                    )
            ) {

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    Text(
                        text = "Reaction",
                        style = headerStyle
                    )

                    Icon(
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = "Get Ready",
                        tint = Color.White,
                        modifier = Modifier
                            .size(120.dp)
                    )

                    Text(
                        text = "Test",
                        style = normalStyle
                    )

                    Text(
                        text = "Click to start",
                        style = normalStyle
                    )

                    Box (
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .shadow(
                                elevation = 15.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = Color.Black,
                                spotColor = Color.Black
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            ),
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(all = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Trial Results",
                                color = finalBlue,
                                style = header2Style
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                var counter = 0
                                for (trialTime in trialResults) {
                                    Column (
                                        //modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "${counter + 1}",
                                            style = header3Style,
                                            color = finalGreen
                                        )

                                        Text(
                                            text = "${trialTime}ms",
                                            style = normalStyle,
                                            color = Color.Black
                                        )

                                        counter++
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        "EndScreen" -> {
            // Calculate Average Score
            var totalTime: Long = 0
            for (trialTime in trialResults) {
                totalTime += trialTime
            }
            trialAverage = (totalTime.toDouble() / trialAttempts).toLong()

            Box (
                modifier = Modifier.background( // 180 250 450
                    color = if (trialAverage < 180) finalGreen
                        else if (trialAverage < 250) finalBlue
                        else if (trialAverage < 450) finalOrange
                        else finalRed
                )
                    .clickable(
                        enabled = true,
                        onClick = {
                            screen = "StartScreen"
                        }
                    )
            ) {

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    Text(
                        text = if (trialAverage < 180) "DANG YOU ARE SO FAST BRO!"
                            else if (trialAverage < 250) "YOUR REFLEX IS GOOD"
                            else if (trialAverage < 450) "MEH LIKE OTHER PERSON"
                            else "YOU LIKE A SNAIL BRO",
                        style = headerStyle
                    )

                    Image (
                        painter = if (trialAverage < 180) painterResource(R.drawable.best)
                            else if (trialAverage < 250) painterResource(R.drawable.good)
                            else if (trialAverage < 450) painterResource(R.drawable.meh)
                            else painterResource(R.drawable.snail),
                        contentDescription = "Image"
                    )

                    Text(
                        text = "Average: ${trialAverage}ms",
                        style = normalStyle
                    )

                    Text(
                        text = "Click to Start New Test",
                        style = normalStyle
                    )

                    Box (
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .shadow(
                                elevation = 15.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = Color.Black,
                                spotColor = Color.Black
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            ),
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(all = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Trial Results",
                                color = finalBlue,
                                style = header2Style
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                var counter = 0
                                for (trialTime in trialResults) {
                                    Column (
                                        //modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "${counter + 1}",
                                            style = header3Style,
                                            color = finalGreen
                                        )

                                        Text(
                                            text = "${trialTime}ms",
                                            style = normalStyle,
                                            color = Color.Black
                                        )

                                        counter++
                                    }
                                }
                            }

                            Text (
                                text = "Average Score",
                                style = header3Style,
                                color = finalBlue
                            )

                            Text (
                                text = "${trialAverage}ms",
                                style = header2Style,
                                color = finalOrange

                            )
                        }
                    }

                }
            }
        }
    }
}



@Preview (showSystemUi = true, showBackground = true)
@Composable
fun ReactionPreview() {
    ReactionView()
}



/* ==============================
========== FUNCTIONS ==========
============================== */


