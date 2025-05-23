package com.example.pawfect_match.ui.mainpages

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.pawfect_match.viewmodel.FavoritesViewModel
import com.example.pawfect_match.viewmodel.PetfinderViewModel
import com.example.pawfect_match.viewmodel.UserViewModel

/**
 * Returns a corresponding emoji for a given animal category name.
 *
 * @param category Animal category as string.
 * @return A string emoji representing the category.
 */
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

///**
// * Represents a simple pet object used in listings.
// *
// * @param name Pet's name.
// * @param breed Pet's breed.
// * @param distance Distance from the user in kilometers.
// * @param imageUrl URL to the pet's photo.
// */
//data class Pet(val name: String, val breed: String, val distance: String, val imageUrl: String)


///**
// * Returns a list of sample pets for display purposes.
// */
//fun getSamplePets() = listOf(
//    Pet("Mochi", "Abyssinian", "3", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
//    Pet("Luna", "Chihuahua", "1.4", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg"),
//    Pet("Casper", "Maine Coon", "2.1", "https://i.pinimg.com/736x/d6/5e/19/d65e197e812eba65f0a407ae198c6805.jpg")
//)

/**
 * Main home screen displaying top banner, category filters, and pet suggestions.
 * Includes bottom navigation and dynamic pet data.
 */
@Composable
// @Preview
fun HomeScreen(navController: NavController, userViewModel: UserViewModel, petfinderViewModel: PetfinderViewModel, favoritesViewModel: FavoritesViewModel) {
    val pets by petfinderViewModel.pets.collectAsState()
    val bottomNavItems = listOf("Home", "Search", "Favorites", "Account")
    var selectedItem by remember { mutableStateOf("Home") }
    val preferredType = remember { mutableStateOf<String?>(null) }
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()



    LaunchedEffect(Unit) {
        userViewModel.getPreferredAnimalType { type ->
            Log.d("Petfinder", "$type")
            preferredType.value = type
            type?.let {
                Log.d("Petfinder", "type")
                petfinderViewModel.loadPetsByType(it)
            }
        }
    }


    /**
     * Bottom navigation bar with icons for main sections.
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            /**
             * Header section with logo, title, search and notifications.
             */
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
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 36.dp)
                        .clickable {  navController.navigate("filters") }
                )

                // Правая иконка: Уведомления
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Banner section promoting adoption.
             */
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

            /**
             * Category grid showing animal types as emoji buttons.
             */
            val categories = listOf("Dogs", "Cats", "Rabbits", "Birds", "Small&Furry", "Fish", "Horse", "Other")
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(180.dp)
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

//            /**
//             * "Pets Near You" section with horizontal scroll of pet cards.
//             */
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("Pets Near You", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.clickable { navController.navigate("results") }
//                ) {
//                    Text(
//                        text = "View All",
//                        color = Color.Green,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
//                        contentDescription = "Arrow",
//                        tint = Color.Green
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                items(pets.take(3)) { pet ->
//                    val painter = rememberAsyncImagePainter(pet.imageUrl)
//                    Log.d("Petfinder", "Photo URL: ${pet.imageUrl}")
//                    Column(modifier = Modifier
//                        .width(120.dp)
//                        .clickable {
//                            petfinderViewModel.setSelectedPet(pet)
//                            navController.navigate("petDetail")
//                        }
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .aspectRatio(1f)
//                                .clip(RoundedCornerShape(16.dp))
//                                .background(Color.LightGray)
//                        ) {
//                            Image(
//                                painter = painter,
//                                contentDescription = "Pet Image",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier.fillMaxSize()
//                            )
//                            Icon(
//                                imageVector = Icons.Default.FavoriteBorder,
//                                contentDescription = "Favorite",
//                                tint = Color.Green,
//                                modifier = Modifier
//                                    .align(Alignment.TopEnd)
//                                    .padding(8.dp)
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(pet.name, fontWeight = FontWeight.SemiBold)
//                        Text("${pet.distance} • ${pet.breed}", fontSize = 12.sp, color = Color.Gray)
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))

            /**
             * "Pets on Your Preferences" section with horizontally scrollable cards.
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pets on Your Preferences", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { navController.navigate("results") }
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
                items(pets.take(3)) { pet ->
                    Log.d("pets", "$pets")
                    val painter = rememberAsyncImagePainter(pet.imageUrl)
                    Log.d("Petfinder", "Photo URL: ${pet.imageUrl}")
                    Column(modifier = Modifier
                        .width(120.dp)
                        .clickable {
                            petfinderViewModel.setSelectedPet(pet)
                            navController.navigate("petDetail")
                        }
                    ) {
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
//                            Image(
//                                painter = painter,
//                                contentDescription = "Pet Image",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier.fillMaxSize()
//                            )
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
                        Text("${pet.distance} • ${pet.breed}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}


