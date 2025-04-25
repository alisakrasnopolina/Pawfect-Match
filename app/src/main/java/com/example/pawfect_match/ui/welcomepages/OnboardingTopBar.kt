package com.example.pawfect_match.ui.welcomepages


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue

// Анимируемый прогресс и общая навигация между шагами с верхней панелью

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

        Text("$step / $totalSteps", fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun OnboardingNavHost() {
    val navController = rememberNavController()
    val steps = listOf(
        "step1", "step2", "step3", "step4"
    )

    NavHost(navController = navController, startDestination = "step1") {
        composable("step1") {
            Column {
                OnboardingTopBar(step = 1) { navController.popBackStack() }
                StepOne_SelectUserType()
            }
        }
        composable("step2") {
            Column {
                OnboardingTopBar(step = 2) { navController.popBackStack() }
                StepTwo_SelectAnimalType()
            }
        }
        composable("step3") {
            Column {
                OnboardingTopBar(step = 3) { navController.popBackStack() }
                StepFour_FinalProfile()
            }
        }
    }
}

// Все шаги (StepOne_SelectUserType, и т.д.) остаются без изменений внутри своего файла