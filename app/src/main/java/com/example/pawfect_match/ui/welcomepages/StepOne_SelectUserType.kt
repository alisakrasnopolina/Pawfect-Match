package com.example.pawfect_match.ui.welcomepages


import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * First step in the onboarding flow.
 * Allows the user to select their role in the app: Pet Owner/Organization or Pet Adopter.
 *
 * Displays the top bar with progress, explanation text, role selection buttons,
 * and a "Continue" button fixed to the bottom.
 */
@Composable
@Preview
fun StepOne_SelectUserType() {
    Column(modifier = Modifier.padding(24.dp)) {
        /**
         * Top bar with progress and back navigation (disabled here).
         */
        OnboardingTopBar(step = 1, onBack = { /* TODO: вернуться назад */ })

        /**
         * Main headline.
         */
        Text("Tell us about yourself", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        /**
         * Description text explaining the role options.
         */
        Text(
            "Are you a Pet Owner or Organization ready to find loving homes? Or a Pet Adopter looking for your new best Friend?",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        /**
         * Option button for Pet Owners or Organizations.
         */
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Pet Owner or Organization", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * Option button for Pet Adopters.
         */
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Pet Adopter", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(32.dp))

        /**
         * Continue button fixed to the bottom of the screen.
         */
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("Continue", color = Color.Black)
            }
        }
    }
}
