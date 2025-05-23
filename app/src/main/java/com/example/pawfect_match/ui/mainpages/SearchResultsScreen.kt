package com.example.pawfect_match.ui.mainpages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import com.example.pawfect_match.data.model.Pet
import com.example.pawfect_match.viewmodel.FavoritesViewModel
import com.example.pawfect_match.viewmodel.PetfinderViewModel

/**
 * Preview for the search results screen with sample pet data.
 */
//@Preview
//@Composable
//fun PreviewSearchResultsScreen() {
//    SearchResultsScreen(
//        pets = listOf(
//            Pet("Mochi", "Abyssinian", "1.2 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
//            Pet("Casper", "Manx", "1.5 km", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg")
//        ),
//        selectedType = "Cats",
//        onTypeSelected = {},
//        // onBackClick = {},
//        onSearchClick = {}
//    )
//}

/**
 * Screen displaying the list of pets filtered by category.
 *
 * @param pets List of pets matching the filter criteria.
 * @param selectedType Currently selected pet type filter.
 * @param onTypeSelected Callback triggered when a filter chip is selected.
 * @param onSearchClick Callback triggered when the search icon is clicked.
 */
@Composable
fun SearchResultsScreen(
    navController: NavController,
    petfinderViewModel: PetfinderViewModel,
    favoritesViewModel: FavoritesViewModel,
    selectedType: String,
    onTypeSelected: (String) -> Unit = {},
) {
    val petTypes = listOf("Dogs", "Cats", "Rabbits", "Birds", "Reptiles", "Fish", "Primates", "Other")
    val bottomNavItems = listOf("Home", "Search", "Favorites", "Account")
    var selectedItem by remember { mutableStateOf("Search") }
    val pets by petfinderViewModel.pets.collectAsState()
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()
    var localSelectedType by remember { mutableStateOf(selectedType) }


    LaunchedEffect(localSelectedType) {
        petfinderViewModel.loadPetsByType(localSelectedType)
    }

//    LaunchedEffect(Unit) {
//        userViewModel.getPreferredAnimalType { type ->
//            Log.d("Petfinder", "$type")
//            preferredType.value = type
//            type?.let {
//                Log.d("Petfinder", "type")
//                petfinderViewModel.loadPetsByType(it)
//            }
//        }
//    }

    Scaffold(
        /**
         * Bottom navigation bar for main navigation.
         */
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
                        onClick = {
                            selectedItem = item
                            when (item) {
                                "Home" -> navController.navigate("home")
                                "Search" -> navController.navigate("results")
                                "Favorites" -> navController.navigate("favorites")
                                "Account" -> navController.navigate("account")
                            }
                        },
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
            /**
             * Top bar with centered title and search icon.
             */
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
                        .clickable {  navController.navigate("filters") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Horizontal row of filter chips representing pet types.
             */
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(petTypes) { type ->
                    FilterChipResults(
                        text = getEmojiForCategory(type) + " " + type,
                        selected = type == localSelectedType,
                        onClick = { localSelectedType = type  }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Grid of pet result cards.
             */
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
                                .clickable {
                                    petfinderViewModel.setSelectedPet(pet)
                                    navController.navigate("petDetail")
                                }
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
                            val isFavorite = favoriteIds.contains(pet.id)
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color.Red else Color.Green,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .clickable { favoritesViewModel.toggleFavorite(pet.id) }
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

/**
 * Composable for a rounded filter chip used to select pet types.
 *
 * @param text The label for the chip (e.g., 🐶 Dogs).
 * @param selected Whether this chip is currently selected.
 * @param onClick Callback when the chip is tapped.
 */
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

