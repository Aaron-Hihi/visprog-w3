package com.aaron.movieapp.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun RegisterView() {
    /* ==============================
    ========== VARIABLES ==========
    ============================== */
    var name by rememberSaveable { mutableStateOf("") }
    var nameIsValid = isValidName(name)

    var email by rememberSaveable { mutableStateOf("") }
    var emailIsValid = isValidEmail(email)

    var password by rememberSaveable { mutableStateOf("") }
    var passwordIsValid = isValidPassword(password)
    var passwordVisible by rememberSaveable { mutableStateOf( false ) }

    var showDialog by rememberSaveable { mutableStateOf( false ) }
    val formValid = nameIsValid && emailIsValid && passwordIsValid


    /* ==============================
    ========== UI LAYOUT ==========
    ============================== */

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {

        // TITLE
        Text(
            text = "Register",
            style = MaterialTheme.typography.titleLarge
        )

        // NAME TEXT FIELD
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text ("Name") },
            isError = name.isNotEmpty() && !nameIsValid
        )

        // EMAIL TEXT FIELD
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text ("Email") },
            isError = email.isNotEmpty() && !emailIsValid
        )

        // PASSWORD TEXT FIELD
        OutlinedTextField(
            // Textfield Handling
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text ("Password") },

            // Password Handling
            isError = password.isNotEmpty() && !passwordIsValid,
            visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),

            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        imageVector =
                            if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                        contentDescription = "Visibility",
                        tint =
                            if (passwordVisible) Color(0xFF82F686)
                            else Color(0xFFFA1E1E)
                    )
                }
            }
        )

        // SUBMIT BUTTON
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = formValid
        ) {
            Text("Submit")
        }




        if (showDialog) {
            AlertDialog (
                onDismissRequest = { showDialog = false },
                title = { Text("Submitted Data") },
                text = { Text("Email: $email\nPassword: $password") },
                confirmButton = {
                    TextButton (
                        onClick = {showDialog = false}
                    ) {
                        Text("Ok")
                    }
                }
            )
        }


    }
}



@Preview (showSystemUi = true, showBackground = true)
@Composable
fun registerPreview() {
    RegisterView()
}



/* ==============================
========== FUNCTIONS ==========
============================== */

private fun isValidName(name: String): Boolean {
    return name.isNotBlank()
}

private fun isValidEmail(email: String): Boolean {
    return email.trim().matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
}

private fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}
