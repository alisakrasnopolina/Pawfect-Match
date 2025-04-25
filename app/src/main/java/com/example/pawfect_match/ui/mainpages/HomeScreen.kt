package com.example.pawfect_match.ui.mainpages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.draw.clip
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.tooling.preview.Preview


fun getEmojiForCategory(category: String): String = when (category) {
    "Dogs" -> "🐕"
    "Cats" -> "🐈"
    "Rabbits" -> "🐇"
    "Small&Furry" -> "🐇"
    "Birds" -> "🦜"
    "Fish" -> "🐠"
    "Barnyard" -> "🐒"
    "Other" -> "🐾"
    else -> ""
}

data class Pet(val name: String, val breed: String, val distance: String, val imageUrl: String)

fun getSamplePets() = listOf(
    Pet("Mochi", "Abyssinian", "3", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
    Pet("Luna", "Chihuahua", "1.4", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
    Pet("Casper", "Maine Coon", "2.1", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg")
)

@Composable
@Preview
fun HomeScreen() {
    val bottomNavItems = listOf("Home", "Search", "Favorites", "Account")
    var selectedItem by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            when (item) {
                                "Home" -> Icon(Icons.Default.Home, contentDescription = item)
                                "Search" -> Icon(Icons.Default.Search, contentDescription = item)
                                "Favorites" -> Icon(Icons.Default.FavoriteBorder, contentDescription = item)
                                "Account" -> Icon(Icons.Default.PersonOutline, contentDescription = item)
                            }
                        },
                        label = { Text(item) },
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Green,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black,
                            indicatorColor = Color.White,
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                // Левая иконка
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = null,
                    tint = Color(0xFF00FF00),
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                // Центр: текст
                Text(
                    text = "Pawfect Match",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Правая иконка: Поиск
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = 36.dp)
                )

                // Правая иконка: Уведомления
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Green)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Just About to\nAdopt?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("See how you can find friends who are a match for you", fontSize = 12.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.align(Alignment.Center), tint = Color.Green)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Categories
            val categories = listOf("Dogs", "Cats", "Rabbits", "Birds", "Small&Furry", "Fish", "Horse", "Other")
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                // modifier = Modifier.height(180.dp)
            ) {
                items(categories) { category ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = getEmojiForCategory(category), fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(category, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pets Near You
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pets Near You", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* TODO: Navigate */ }
                ) {
                    Text(
                        text = "View All",
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow",
                        tint = Color.Green
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(getSamplePets()) { pet ->
                    Column(modifier = Modifier.width(120.dp)) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(pet.name, fontWeight = FontWeight.SemiBold)
                        Text("1.2 km • ${pet.breed}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            // Pets on Your Preferences
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pets on Your Preferences", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* TODO: Navigate */ }
                ) {
                    Text(
                        text = "View All",
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow",
                        tint = Color.Green
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(getSamplePets()) { pet ->
                    Column(modifier = Modifier.width(120.dp)) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(pet.name, fontWeight = FontWeight.SemiBold)
                        Text("1.2 km • ${pet.breed}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}


