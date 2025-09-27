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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun ColorMatchView() {
    /* ==============================
    ========== VARIABLES ==========
    ============================== */
    // Gameplay
    var question: Question by remember { mutableStateOf(generateQuestion()) }
    var questionNumber: Int by rememberSaveable { mutableIntStateOf(0) }
    var correctAmount by rememberSaveable { mutableIntStateOf(0) }
    var wrongAmount by rememberSaveable { mutableIntStateOf(0) }
    var questionTimerText by rememberSaveable { mutableStateOf("5") }

    val maxWrongAmount = 3

    // Game
    //val isGameOver = isGameOver(wrongAmount, maxWrongAmount)
    var state by rememberSaveable { mutableStateOf(State.START) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }

    // Utils
    var countDownText by rememberSaveable { mutableStateOf("3") }


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
    Box for background and content alignment
    1. Start
       - Column{ Column{ Text, Text, Text}, Box{Text}}
       - Box is clickable, state = State.COUNTDOWN

    2. Countdown
       - Text
       - text = countDownText
       - LaunchedEffect -> change countDownText

    3. Playing
       - Column{ Row{Text, Text}, Column{Text, Text}, Row{
         Box{ Text}, Box{ Text}}}
       - Generate Question
       --- Random Mode
       --- Random Text and Color -> ColorName.TEXT ColorName.COLOR
       --- Random textChoiceAtLeft
       --- correct Answer = if Mode.COLOR then choice.COLOR, else choice.TEXT
       - questionTimer = 5
       - Correct if Choice = Question.correctChoice
       - If correct:
       --- correctAmount, loop
       - If false or questionTimer = 0:
       --- wrongAmount++, loop
       - Loop if wrongAmount < 3

    4. GameOver
       - Column{ Text, Spacer, Column{ Text, text}, Column{ Text, text},
         Box{ Text}, Box{ Text}
       - If correctAmount > bestScore, bestScore = correctAmount
       - Restart -> Countdown
       - Exit -> Start
     */

    // COUNTDOWN LAUNCHED EFFECT
    LaunchedEffect(state) {
        if (state == State.COUNTDOWN) {
            // Initialize
            questionNumber = 0
            correctAmount = 0
            wrongAmount = 0

            // Start at 3, going down
            var countdown = 3
            while (countdown > 0) {
                countDownText = countdown.toString()
                delay(timeMillis = 1000L)
                countdown--
            }

            // After done
            countDownText = "Start!"
            delay(timeMillis = 1000L)

            state = State.PLAYING
        }
    }

    // COUNTDOWN FOR QUESTION
    LaunchedEffect(state, questionNumber) {
        if (state == State.PLAYING) {
            // Start at 5, going down
            var questionTimer = 5
            while (questionTimer > 0) {
                questionTimerText = questionTimer.toString()
                delay(timeMillis = 1000L)
                questionTimer--
            }

            // If time out -> Wrong
            wrongAmount++
            if (isGameOver(wrongAmount, maxWrongAmount)) state = State.GAMEOVER
            else {
                question = generateQuestion()
                questionNumber++
            }
        }
    }


    // LAYOUT
    // Background
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = lightGray
            ),
        contentAlignment = Alignment.Center
    ) {
        // Changing state
        when (state) {
            State.START -> {

                // Layout
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        alignment = Alignment.CenterVertically, space = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title Text
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome",
                            style = headerStyle
                        )

                        Text(
                            text = "to",
                            style = headerStyle
                        )

                        Text(
                            text = "Color Word Matching",
                            style = headerStyle
                        )
                    }

                    // Button
                    Box (
                        modifier = Modifier
                            .background(
                                color = gray,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 32.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    state = State.COUNTDOWN
                                }
                            ),

                    ) {
                        Text (
                            text = "Start Game",
                            style = normalStyle
                        )
                    }
                }

            }

            State.COUNTDOWN -> {
                Text (
                    text = countDownText,
                    style = headerStyle
                )
            }

            State.PLAYING -> {
                // Layout
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        alignment = Alignment.CenterVertically, space = 84.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Information bar
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text (
                            text = "Mode: ${question.mode}",
                            style = normalStyle
                        )

                        Text (
                            text = "✅ $correctAmount     ❌ $wrongAmount/$maxWrongAmount",
                            style = normalStyle
                        )
                    }

                    // Question
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            alignment = Alignment.CenterVertically, space = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text (
                            text = "$questionTimerText s",
                            style = normalStyle
                        )

                        Text (
                            text = question.text.colorName,
                            style = headerStyle,
                            color = question.color.color
                        )
                    }

                    // Choices
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // Left choice answer
                        Box (
                            modifier = Modifier
                                .background(
                                    color = gray,
                                    shape = RoundedCornerShape(32.dp)
                                )
                                .padding(16.dp)
                                .width(64.dp)
                                .clickable(
                                    enabled = true,
                                    onClick = {
                                        if (checkAnswer(question.leftChoice, question)) correctAmount++
                                        else wrongAmount++

                                        if (isGameOver(wrongAmount, maxWrongAmount)) state = State.GAMEOVER
                                        else {
                                            question = generateQuestion()
                                            questionNumber++
                                        }
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text (
                                text = question.leftChoiceString,
                                style = normalStyle
                            )
                        }

                        // Right choice answer
                        Box (
                            modifier = Modifier
                                .background(
                                    color = gray,
                                    shape = RoundedCornerShape(32.dp)
                                )
                                .padding(16.dp)
                                .width(64.dp)
                                .clickable(
                                    enabled = true,
                                    onClick = {
                                        if (checkAnswer(question.rightChoice, question)) correctAmount++
                                        else wrongAmount++

                                        if (isGameOver(wrongAmount, maxWrongAmount)) state = State.GAMEOVER
                                        else {
                                            question = generateQuestion()
                                            questionNumber++
                                        }
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text (
                                text = question.rightChoiceString,
                                style = normalStyle
                            )
                        }
                    }
                }

            }

            State.GAMEOVER -> {
                // Logic
                if (correctAmount > bestScore) bestScore = correctAmount

                // Layout
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 48.dp, horizontal = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        alignment = Alignment.CenterVertically, space = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text (
                        text = "Game Over!",
                        style = headerStyle
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    // Your score
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your Score",
                            style = header2Style
                        )

                        Text (
                            text = "$correctAmount",
                            style = header2Style
                        )
                    }

                    // Best score
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Best Score",
                            style = normalStyle
                        )

                        Text (
                            text = "$bestScore",
                            style = normalStyle
                        )
                    }

                    // Button - Restart
                    Box (
                        modifier = Modifier
                            .background(
                                color = gray,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 32.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    state = State.COUNTDOWN
                                }
                            ),

                        ) {
                        Text (
                            text = "Restart Game",
                            style = normalStyle
                        )
                    }

                    // Button - Exit
                    Box (
                        modifier = Modifier
                            .background(
                                color = gray,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 32.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    state = State.START
                                }
                            ),

                        ) {
                        Text (
                            text = "Exit",
                            style = normalStyle
                        )
                    }
                }
            }
        }
    }
}



@Preview (showSystemUi = true, showBackground = true)
@Composable
fun ColorMatchPreview() {
    ColorMatchView()
}



/* ==============================
============= ENUM ==============
============================== */

// Show which screen
private enum class State {
    START, COUNTDOWN, PLAYING, GAMEOVER
}

// Color -> Match with color, Text -> Match with text
private enum class Mode {
    COLOR, TEXT
}

// Color -> Match with color, Text -> Match with text
private enum class Choice {
    COLOR, TEXT
}

// Color
private enum class ColorName(val colorName: String, val color: Color) {
    RED("RED", Color.Red),
    YELLOW("YELLOW",Color.Yellow),
    GREEN("GREEN",Color.Green),
    BLUE("BLUE", Color.Blue),
    PURPLE("PURPLE", Color(0xFF4F00AF))
}




/* ==============================
========== DATA CLASS ===========
============================== */
private data class Question (
    val mode: Mode,
    val text: ColorName,
    val color: ColorName,
    val leftChoice: Choice,
    val rightChoice: Choice,
    val correctAnswer: Choice
) {
    // Instead of choosing TEXT/COLOR, choose ColorName instead
    private fun Choice.toDisplayString(): String =
        if (this == Choice.COLOR) color.colorName
        else text.colorName

    val leftChoiceString get() = leftChoice.toDisplayString()
    val rightChoiceString get() = rightChoice.toDisplayString()
}


/* ==============================
========== FUNCTIONS ==========
============================== */
private fun generateQuestion(): Question {
    // Questions
    val mode = Mode.entries.toTypedArray().random()
    val text = ColorName.entries.toTypedArray().random()
    var color = ColorName.entries.toTypedArray().random()
    while (color == text) {
        color = ColorName.entries.random()
    }

    // Choices
    val leftChoice: Choice
    val rightChoice: Choice
    val textChoiceAtLeft = listOf(true, false).random()
    if (textChoiceAtLeft) {
        leftChoice = Choice.TEXT
        rightChoice = Choice.COLOR
    } else {
        leftChoice = Choice.COLOR
        rightChoice = Choice.TEXT
    }

    // Correct Answer
    val correctAnswer = if (mode == Mode.COLOR) Choice.COLOR
    else Choice.TEXT

    return Question(mode, text, color, leftChoice, rightChoice, correctAnswer)
}

private fun isGameOver(currentWrongs: Int, maxWrongs: Int): Boolean {
    return currentWrongs >= maxWrongs
}

private fun checkAnswer(answer: Choice, question: Question): Boolean {
    return answer == question.correctAnswer
}



