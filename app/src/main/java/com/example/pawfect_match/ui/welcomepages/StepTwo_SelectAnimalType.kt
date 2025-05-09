package com.example.pawfect_match.ui.welcomepages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawfect_match.viewmodel.UserViewModel

/**
 * Step two in the onboarding flow where the user selects a type of animal to adopt.
 *
 * Displays a grid of emoji animal icons with labels. Selection is stored locally.
 * Includes a "Continue" button and onboarding progress bar.
 */
@Composable
//@Preview
fun StepTwo_SelectAnimalType(navController: NavController, userViewModel: UserViewModel) {
    val selectedIndex = remember { mutableStateOf(-1) }

    /**
     * List of animal types represented as emoji + label (split by newline).
     */
    val animalTypes = listOf(
        "\uD83D\uDC15 \n Dogs",
        "\uD83D\uDC08 \n Cats",
        "\uD83D\uDC07 \n Rabbits",
        "\uD83D\uDC01 \n Small&Furry",
        "\uD83D\uDC0E \n Horse",
        "\uD83E\uDD9C \n Birds",
        "\uD83D\uDC20 \n Fish",
        "\uD83D\uDC16 \n Barnyard",
        "\uD83D\uDC3E \n Other"
    )

    /**
     * Root layout containing title, grid of animal types, and continue button.
     */
    Column(modifier = Modifier.padding(24.dp).fillMaxHeight()) {
        /**
         * Step progress indicator with back button (step 2 of 3).
         */
        OnboardingTopBar(step = 2, onBack = { navController.popBackStack() })

        /**
         * Main headline and explanation.
         */
        Text("Let's Find Your Match!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "What type of animal are you looking to adopt? Don't worry, you can always change this later.",
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(24.dp))

        /**
         * Grid layout for animal type selection.
         */
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            // modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(animalTypes) { index, item ->
                val isSelected = index == selectedIndex.value
                OutlinedButton(
                    onClick = { selectedIndex.value = index },
                    border = BorderStroke(
                        width = 2.dp,
                        color = if (isSelected) Color.Green else Color.LightGray
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) Color(0xFFE0FFE0) else Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    /**
                     * Inside each button: emoji and label vertically centered.
                     */
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.substringBefore("\n").trim(), // 🐶
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.substringAfter("\n").trim(), // Собаки
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        /**
         * Continue button anchored to the bottom of the screen.
         */
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    val type = animalTypes.getOrNull(selectedIndex.value)
                    type?.let {
                        userViewModel.updateField("preferredAnimalType", it)
                        navController.navigate("onboarding3")
                    }
                },
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
