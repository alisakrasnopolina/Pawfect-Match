package com.example.pawfect_match.ui.registration


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawfect_match.data.firebase.FirebaseAuthManager
import com.example.pawfect_match.viewmodel.AuthViewModel

/**
 * Composable screen that allows new users to sign up for the Pawfect Match app.
 * Includes fields for email and password, agreement to terms, and navigation to login.
 */
@Composable
//@Preview
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel) {
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val agreeTerms = remember { mutableStateOf(false) }

    /**
     * Main scrollable vertical layout.
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        /**
//         * Back arrow icon (navigation not implemented yet).
//         */
//        IconButton(
//            onClick = { /* Back navigation */ },
//            modifier = Modifier.align(Alignment.Start)
//        ) {
//            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * Section with intro text and input fields.
         */
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Join Pawfect Match \uD83D\uDC3E", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text("A world of furry possibilities awaits you.", fontSize = 16.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            /**
             * Email input field.
             */
            Text("Email")
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            /**
             * Password input field with visibility toggle.
             */
            Text("Password")
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * Agreement checkbox for terms and conditions.
         */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = agreeTerms.value,
                onCheckedChange = { agreeTerms.value = it },
                colors = CheckboxDefaults.colors(checkedColor = Color.Green) // custom orange
            )
            Text("I agree to Pawfect Match ")
            Text(
                text = "Terms & Conditions",
                color = Color.Green,
                modifier = Modifier.clickable { /* Open T&C */ },
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        /**
         * Link to login screen for existing users.
         */
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account? ")
            Text(
                text = "Sign In",
                modifier = Modifier.clickable {  navController.navigate("login") },
                color = Color.Green,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

//        Text("or", color = Color.Gray)
//
//        Spacer(modifier = Modifier.height(16.dp))

//        GoogleSignInButton(onClick = { /* TODO */ })
//        Spacer(modifier = Modifier.height(12.dp))
//        AppleSignInButton(onClick = { /* TODO */ })

        Spacer(modifier = Modifier.height(32.dp))

    }

    /**
     * Sign up button fixed to the bottom of the screen.
     */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Button(
            onClick = {
                if (!agreeTerms.value) {
                    Toast.makeText(
                        context,
                        "You must agree to Terms & Conditions",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                if (password.value.length < 6) {
                    Toast.makeText(
                        context,
                        "Password must be at least 6 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                viewModel.register(
                    email = email.value,
                    password = password.value
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Sign up", color = Color.Black)
        }

        when (authState) {
            is AuthViewModel.AuthStatus.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            is AuthViewModel.AuthStatus.Error -> {
//                   Text((authState as AuthViewModel.AuthStatus.Error).message, color = Color.Red)
                Toast.makeText(context, (authState as AuthViewModel.AuthStatus.Error).message, Toast.LENGTH_SHORT).show()
//                Text(
//                    (authState as AuthViewModel.AuthStatus.Error).message,
//                    color = Color.Red,
//                    modifier = Modifier.align(Alignment.Center)
//                )
            }
//            is AuthViewModel.AuthStatus.Success -> {
//                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
//                navController.navigate("onboarding1") {
//                    popUpTo("signup") { inclusive = true }
//                }
//            }
            else -> {}
        }

        if (authState is AuthViewModel.AuthStatus.Success && FirebaseAuthManager.currentUser() != null) {
            LaunchedEffect(authState) {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                navController.navigate("onboarding1") {
                    popUpTo("signup") { inclusive = true }
                }
                viewModel.resetAuthState()
            }
        }
    }
}
