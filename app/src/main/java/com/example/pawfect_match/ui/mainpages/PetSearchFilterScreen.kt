package com.example.pawfect_match.ui.mainpages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@OptIn(ExperimentalLayoutApi::class)
@Preview
fun PetSearchFilterScreen(onSearchClick: () -> Unit = {}) {
    val selectedType = remember { mutableStateOf("Cats") }
    val selectedGender = remember { mutableStateOf("Male") }
    val selectedSize = remember { mutableStateOf("Medium") }
    val selectedAge = remember { mutableStateOf("Adult") }

    val types = listOf("Dogs", "Cats", "Rabbits", "Birds", "Reptiles", "Fish", "Primates", "Other")
    val genders = listOf("Any", "Male", "Female")
    val sizes = listOf("Small", "Medium", "Large")
    val ages = listOf("Baby", "Young", "Adult", "Senior")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text("Pet Search", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
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
