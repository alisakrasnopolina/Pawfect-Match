package com.example.pawfect_match.ui.mainpages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * Composable screen for filtering pet search results.
 * Users can filter by type, gender, size, age, and (optionally) location.
 *
 * @param onSearchClick Callback triggered when the "Search" button is pressed.
 */
@Composable
@OptIn(ExperimentalLayoutApi::class)
// @Preview
fun PetSearchFilterScreen(navController: NavController, onSearchClick: () -> Unit = {}) {
    val selectedType = remember { mutableStateOf("") }
    val selectedGender = remember { mutableStateOf("") }
    val selectedSize = remember { mutableStateOf("") }
    val selectedAge = remember { mutableStateOf("") }

    val types = listOf("Dogs", "Cats", "Rabbits", "Birds", "Reptiles", "Fish", "Primates", "Other")
    val genders = listOf("Any", "Male", "Female")
    val sizes = listOf("Small", "Medium", "Large")
    val ages = listOf("Baby", "Young", "Adult", "Senior")

    /**
     * Scrollable filter section.
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        /**
         * Top bar with title and back icon.
         */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                .clickable { navController.popBackStack() })
            Spacer(modifier = Modifier.width(16.dp))
            Text("Pet Search", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        /**
         * Location section (currently static).
         */
        Text("Location", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4F4F4), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Place, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("New York, NY, US")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /**
         * Pet type filters.
         */
        Text("Pet Types", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            types.forEach { type ->
                FilterChip(type, selectedType.value == type) { selectedType.value = type }
            }
        }

        /**
         * Gender filters (optional).
         */
        Spacer(modifier = Modifier.height(24.dp))
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                append("Gender ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("(Optional)")
            }
        },
            fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            genders.forEach { gender ->
                FilterChip(gender, selectedGender.value == gender) { selectedGender.value = gender }
            }
        }

        /**
         * Size filters (optional).
         */
        Spacer(modifier = Modifier.height(24.dp))
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                append("Size ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("(Optional)")
            }
        },
            fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            sizes.forEach { size ->
                FilterChip(size, selectedSize.value == size) { selectedSize.value = size }
            }
        }

        /**
         * Age filters (optional).
         */
        Spacer(modifier = Modifier.height(24.dp))
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                append("Age ")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("(Optional)")
            }
        },
            fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ages.forEach { age ->
                FilterChip(age, selectedAge.value == age) { selectedAge.value = age }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    /**
     * Bottom "Search" button.
     */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Search", color = Color.Black)
        }
    }
}

/**
 * Custom filter chip used for selecting options in the pet filter screen.
 *
 * @param text The label displayed inside the chip.
 * @param selected Whether the chip is currently selected.
 * @param onClick Callback when the chip is tapped.
 */
@Composable
fun FilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val background = if (selected) Color.Green else Color.White
    val contentColor = if (selected) Color.White else Color.Black
    Surface(
        shape = RoundedCornerShape(50),
        color = background,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = contentColor
        )
    }
}
