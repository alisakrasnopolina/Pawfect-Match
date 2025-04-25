package com.example.pawfect_match.ui.mainpages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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

/**
 * Preview for the Favorites screen using sample pet data.
 */
@Preview
@Composable
fun PreviewFavoritesScreen() {
    val samplePets = listOf(
        Pet("Mochi", "Abyssinian", "1.2 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
        Pet("Clover", "Fauve de Bourgogne", "1.2 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
        Pet("Cleo", "Manx", "1.5 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
        Pet("Luna", "Chihuahua", "1.2 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
        Pet("Fluffy", "Samoyed", "1.3 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
        Pet("Chip", "Squirrel", "1.4 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg")
    )
    FavoritesScreen(pets = samplePets, onPetClick = {})
}

/**
 * Composable screen that displays the user's favorite pets.
 *
 * @param pets The list of pets the user has favorited.
 * @param onPetClick Callback triggered when a pet card is clicked.
 */
@Composable
fun FavoritesScreen(pets: List<Pet>, onPetClick: (Pet) -> Unit) {
    val bottomNavItems = listOf("Home", "Search", "Favorites", "Account")
    var selectedItem by remember { mutableStateOf("Favorites") }

    /**
     * Scaffold layout with bottom navigation.
     */
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            /**
             * Header with icon and number of favorite pets.
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Favorites (${pets.size})", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TODO: Add filter chips like "All", "Dogs", "Cats", etc.

            /**
             * Grid layout displaying all favorite pets.
             */
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(pets) { pet ->
                    Column(
                        modifier = Modifier.clickable { onPetClick(pet) }
                    ) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
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
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = Color.Green,
                                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(pet.name, fontWeight = FontWeight.SemiBold)
                        Text(
                            "\uD83D\uDCCD ${pet.distance} • ${pet.breed}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
