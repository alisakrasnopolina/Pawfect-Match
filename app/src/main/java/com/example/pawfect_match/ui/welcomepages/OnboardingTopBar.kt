package com.example.pawfect_match.ui.welcomepages


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pawfect_match.viewmodel.UserViewModel

/**
 * A composable top bar used in onboarding steps.
 * Includes a back arrow, animated progress bar, and a step indicator (e.g. "2 / 3").
 *
 * @param step The current step number (1-based).
 * @param totalSteps Total number of onboarding steps.
 * @param onBack Callback triggered when the back arrow is pressed.
 */
@Composable
fun OnboardingTopBar(
    step: Int,
    totalSteps: Int = 3,
    onBack: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = step / totalSteps.toFloat(),
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        /**
         * Back arrow icon button.
         */
        Box(modifier = Modifier
            .size(24.dp)
            .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(20.dp)
            )
        }

        /**
         * Animated linear progress bar indicating step completion.
         */
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(50)),
            color = Color.Green,
            trackColor = Color(0xFFEAEAEA)
        )

        /**
         * Step label showing the current and total step count.
         */
        Text("$step / $totalSteps", fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

/**
 * A navigation container managing the onboarding step flow.
 * Displays a step-specific top bar and composable content for each step.
 *
 * Uses Jetpack Navigation Compose and a simple NavHost with route names "step1", "step2", etc.
 */
@Composable
fun OnboardingNavHost() {
    val navController = rememberNavController()
    val steps = listOf(
        "step1", "step2", "step3", "step4"
    )
    val userViewModel: UserViewModel = viewModel()

    /**
     * Navigation host handling each step in the onboarding flow.
     */
    NavHost(navController = navController, startDestination = "step1") {

        composable("step1") {
            Column {
                OnboardingTopBar(step = 1) { navController.popBackStack() }
                StepOne_SelectUserType(navController, userViewModel)
            }
        }
        composable("step2") {
            Column {
                OnboardingTopBar(step = 2) { navController.popBackStack() }
                StepTwo_SelectAnimalType(navController, userViewModel)
            }
        }
        composable("step3") {
            Column {
                OnboardingTopBar(step = 3) { navController.popBackStack() }
                StepFour_FinalProfile(navController, userViewModel)
            }
        }
    }
}
