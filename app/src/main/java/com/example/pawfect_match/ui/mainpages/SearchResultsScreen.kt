package com.example.pawfect_match.ui.mainpages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Preview
@Composable
fun PreviewSearchResultsScreen() {
    SearchResultsScreen(
        pets = listOf(
            Pet("Mochi", "Abyssinian", "1.2 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
            Pet("Casper", "Manx", "1.5 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg")
        ),
        selectedType = "Cats",
        onTypeSelected = {},
        // onBackClick = {},
        onSearchClick = {}
    )
}

@Composable
fun SearchResultsScreen(
    pets: List<Pet>,
    selectedType: String,
    onTypeSelected: (String) -> Unit = {},
    // onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    val petTypes = listOf("Dogs", "Cats", "Rabbits", "Birds", "Reptiles", "Fish", "Primates", "Other")
    val bottomNavItems = listOf("Home", "Search", "Favorites", "Account")
    var selectedItem by remember { mutableStateOf("Search") }

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
        Column( modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("Search Results", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onSearchClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pet Type Filters
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(petTypes) { type ->
                    FilterChipResults(
                        text = getEmojiForCategory(type) + " " + type,
                        selected = type == selectedType,
                        onClick = { onTypeSelected(type) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grid of Pets
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = pets) { pet ->
                    Column(horizontalAlignment = Alignment.Start) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.LightGray)
                        ) {
                            AsyncImage(
                                model = pet.imageUrl,
                                contentDescription = "Pet Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = Color.Green,
                                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(pet.name, fontWeight = FontWeight.SemiBold)
                        Text(
                            "📍 ${pet.distance} • ${pet.breed}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChipResults(text: String, selected: Boolean, onClick: () -> Unit) {
    val background = if (selected) Color.Green else Color.White
    val contentColor = if (selected) Color.White else Color.Black
    Surface(
        shape = RoundedCornerShape(50),
        color = background,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = contentColor
        )
    }
}

