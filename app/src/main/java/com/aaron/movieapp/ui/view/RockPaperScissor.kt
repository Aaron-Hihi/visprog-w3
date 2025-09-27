package com.aaron.movieapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun RockPaperScissorView() {
    /* ==============================
    ========== VARIABLES ==========
    ============================== */
    // Gameplay Loop
    var playerPick by rememberSaveable { mutableStateOf(Pick.ROCK) }
    var cpuPick by rememberSaveable { mutableStateOf(Pick.ROCK) }
    var playerScore by rememberSaveable { mutableIntStateOf(0) }
    var cpuScore by rememberSaveable { mutableIntStateOf(0) }
    var roundResult by rememberSaveable { mutableStateOf(Result.DRAW) }

    // Game
    var state by rememberSaveable { mutableStateOf(RPSState.START) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }

    // Max round
    var currentRound by rememberSaveable { mutableIntStateOf(1) }
    var maxRounds by rememberSaveable { mutableIntStateOf(3) }
    val canAddRound = canAddRound(maxRounds)
    val canRemoveRound = canRemoveRound(maxRounds)


    /* ==============================
    ========== UI LAYOUT ==========
    ============================== */
    // STYLES
    val headerStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Light,
        color = Color.Black,
        textAlign = TextAlign.Center,
    )

    val header2Style = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Light,
        color = Color.Black,
        textAlign = TextAlign.Center,
    )

    val normalStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Light,
        color = Color.Black,
        textAlign = TextAlign.Center,
        )


    // COLORS
    val lightGray = Color(0xFFEAEAEA)
    val gray = Color(0xFFB1B4B9)

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

    LaunchedEffect(state) {
        if (state == RPSState.REVEAL) {
            // Check result
            when (roundResult) {
                Result.WIN -> playerScore++
                Result.DRAW -> { currentRound-- }
                Result.LOSE -> cpuScore++
            }

            // Wait
            delay(timeMillis = 700L)

            // Is match finished logic
            val currentMaxScore = (maxRounds / 2) + 1
            state = if (playerScore >= currentMaxScore || cpuScore >= currentMaxScore) {
                RPSState.FINISHED
            } else {
                RPSState.PICK
            }
        }
    }


    // LAYOUT
    // Background
    Box (
        modifier = Modifier.background(
            color = lightGray
        )
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        when (state) {
            RPSState.START -> {
                // Initialize
                playerScore = 0
                cpuScore = 0

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 80.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    // Information Nav
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text (
                            text = "😎 $playerScore - $cpuScore 🤖",
                            style = normalStyle
                        )

                        Text (
                            text = "Best of $maxRounds",
                            style = normalStyle
                        )
                    }

                    // Title + Best of ... selection + Button
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            alignment = Alignment.CenterVertically,
                            space = 16.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(
                            text = "Rock • Paper • Scissors",
                            style = headerStyle
                        )

                        // Best of ... selection
                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Remove round if not best of 3
                            Icon (
                                imageVector = Icons.Default.RemoveCircle,
                                contentDescription = "Remove",
                                tint = if (canRemoveRound) Color.Black
                                    else gray,
                                modifier = Modifier
                                    .clickable(
                                        enabled = canRemoveRound,
                                        onClick = {
                                            maxRounds -= 2
                                        }
                                    )

                            )

                            // Show max rounds
                            Text (
                                text = "Best of $maxRounds",
                                style = header2Style
                            )

                            // Add round if not best of 7
                            Icon (
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Add",
                                tint = if (canAddRound) Color.Black
                                else gray,
                                modifier = Modifier
                                    .clickable(
                                        enabled = canAddRound,
                                        onClick = {
                                            maxRounds += 2
                                        }
                                    )

                            )
                        }

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        // Start button
                        Box (
                            modifier = Modifier
                                .background(
                                    color = Color.Black,
                                    shape = RoundedCornerShape(32.dp)
                                )
                                .padding(vertical = 8.dp, horizontal = 32.dp)
                                .width(128.dp)
                                .clickable(
                                    enabled = true,
                                    onClick = {
                                        state = RPSState.PICK
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text (
                                text = "Start",
                                style = normalStyle,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            RPSState.PICK -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 80.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    // Information Nav
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "😎 $playerScore - $cpuScore 🤖",
                            style = normalStyle
                        )

                        Text(
                            text = "Best of $maxRounds",
                            style = normalStyle
                        )
                    }


                    // Pick your move layout
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            alignment = Alignment.CenterVertically,
                            space = 16.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        // Instruction
                        Text(
                            text = "Pick your move!",
                            style = normalStyle
                        )

                        Text(
                            text = "❔ vs ❔",
                            style = headerStyle
                        )

                        Spacer (
                            modifier = Modifier.height(4.dp)
                        )

                        // RPS Selection
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Logic
                            cpuPick = Pick.entries.toTypedArray().random()

                            // Rock Button
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .padding(vertical = 12.dp, horizontal = 12.dp)
                                    .width(60.dp)
                                    .clickable(
                                        enabled = true,
                                        onClick = {
                                            playerPick = Pick.ROCK
                                            state = RPSState.REVEAL
                                            roundResult = checkWinner(playerPick, cpuPick)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✊ Rock",
                                    style = normalStyle,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            }

                            // Paper Button
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .padding(vertical = 12.dp, horizontal = 12.dp)
                                    .width(60.dp)
                                    .clickable(
                                        enabled = true,
                                        onClick = {
                                            playerPick = Pick.PAPER
                                            state = RPSState.REVEAL
                                            roundResult = checkWinner(playerPick, cpuPick)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✋ Paper",
                                    style = normalStyle,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            }

                            // Scissors Button
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .padding(vertical = 12.dp, horizontal = 12.dp)
                                    .width(60.dp)
                                    .clickable(
                                        enabled = true,
                                        onClick = {
                                            playerPick = Pick.SCISSORS
                                            state = RPSState.REVEAL
                                            roundResult = checkWinner(playerPick, cpuPick)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "✌ Scissors",
                                    style = normalStyle,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }



            }

            RPSState.REVEAL -> {
                // Logic
                val resultDisplay: String

                when (roundResult) {
                    Result.WIN -> {
                        resultDisplay = "Win"
                    }
                    Result.DRAW -> {
                        resultDisplay = "Draw"
                    }
                    Result.LOSE -> {
                        resultDisplay = "Lose"
                    }
                }

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 80.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    // Information Nav
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "😎 $playerScore - $cpuScore 🤖",
                            style = normalStyle
                        )

                        Text(
                            text = "Best of $maxRounds",
                            style = normalStyle
                        )
                    }


                    // Pick your move layout
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            alignment = Alignment.CenterVertically,
                            space = 16.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        // Instruction
                        Text(
                            text = "${emojifyPick(playerPick)} vs ${emojifyPick(cpuPick)}",
                            style = headerStyle
                        )

                        Text(
                            text = resultDisplay,
                            style = normalStyle
                        )
                    }
                }
            }

            RPSState.FINISHED -> {
                // Logic
                if ((playerScore - cpuScore) > bestScore) bestScore = (playerScore - cpuScore)

                // Layout
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 80.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp)

                ) {
                    // Information Nav
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "😎 $playerScore - $cpuScore 🤖",
                            style = normalStyle
                        )

                        Text(
                            text = "Best of $maxRounds",
                            style = normalStyle
                        )
                    }


                    // Pick your move layout
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            alignment = Alignment.CenterVertically,
                            space = 16.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        // Title
                        Text(
                            text = if (playerScore > cpuScore) "You Win the Match!"
                                    else "You Lose the Match!",
                            style = headerStyle
                        )

                        Text(
                            text = "Best Score: $bestScore",
                            style = normalStyle
                        )

                        // Button
                        // RPS Selection
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Restart Button
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .padding(vertical = 12.dp, horizontal = 12.dp)
                                    .width(60.dp)
                                    .clickable(
                                        enabled = true,
                                        onClick = {
                                            // Restart
                                            playerScore = 0
                                            cpuScore = 0
                                            state = RPSState.PICK
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Restart",
                                    style = normalStyle,
                                    color = Color.White
                                )
                            }

                            // Exit Button
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .padding(vertical = 12.dp, horizontal = 12.dp)
                                    .width(60.dp)
                                    .clickable(
                                        enabled = true,
                                        onClick = {
                                            state = RPSState.START
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Exit",
                                    style = normalStyle,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview (showSystemUi = true, showBackground = true)
@Composable
fun RockPaperScissorPreview() {
    RockPaperScissorView()
}


/* ==============================
============= ENUM ==============
============================== */
private enum class RPSState {
    START, PICK, REVEAL, FINISHED
}

private enum class Pick {
    ROCK, PAPER, SCISSORS
}

private enum class Result {
    WIN, DRAW, LOSE
}


/* ==============================
========== FUNCTIONS ==========
============================== */
private fun checkWinner(playerPick: Pick, cpuPick: Pick): Result {
    return if (playerPick == cpuPick) Result.DRAW
    else if ((playerPick == Pick.ROCK && cpuPick == Pick.SCISSORS) ||
            (playerPick == Pick.PAPER && cpuPick == Pick.ROCK) ||
            (playerPick == Pick.SCISSORS && cpuPick == Pick.PAPER)) Result.WIN

    else Result.LOSE
}

private fun canAddRound(currentMaxRound: Int): Boolean {
    return currentMaxRound < 7
}

private fun canRemoveRound(currentMaxRound: Int): Boolean {
    return currentMaxRound > 3
}


private fun emojifyPick(pick: Pick): String {
    when (pick) {
        Pick.ROCK -> return "✊"
        Pick.PAPER -> return "✋"
        Pick.SCISSORS -> return "✌"
    }
}
