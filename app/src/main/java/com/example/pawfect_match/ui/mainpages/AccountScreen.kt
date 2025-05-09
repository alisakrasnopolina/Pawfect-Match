package com.example.pawfect_match.ui.mainpages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.pawfect_match.data.firebase.FirebaseAuthManager
import com.example.pawfect_match.viewmodel.UserViewModel

//@Preview
//@Composable
//fun AccountScreenPreview(navController: NavController, userViewModel: UserViewModel, onLogout: () -> Unit) {
//    AccountScreen()
//}

/**
 * Main screen displaying user account information.
 * Includes user avatar, personal details and action buttons like Edit and Logout.
 */
@Composable
fun AccountScreen(
    navController: NavController = rememberNavController(),
    userViewModel: UserViewModel = viewModel(),
    onLogout: () -> Unit
) {
    var selectedItem by remember { mutableStateOf("Account") }
    val user by userViewModel.user.collectAsState()

    val name = user?.name ?: "..."
    val email = user?.email ?: "..."
    val phone = user?.phone ?: "..."
    val gender = user?.gender ?: "..."
    val profileImage = user?.profileImageUrl ?: ""
    val birthDate = user?.birthdate ?: "..."

    Scaffold(
        bottomBar = {
            val bottomNavItems = listOf("Home", "Search", "Favorites", "Account")
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            /**
             * Bottom navigation bar used to navigate between app sections.
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("My Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            /**
             * Profile image (currently static).
             */
            Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                AsyncImage(
                    model = "",
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
//                    Box(
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clip(CircleShape)
//                            .background(Color.Green)
//                            .padding(4.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Edit,
//                            contentDescription = "Edit",
//                            modifier = Modifier.align(Alignment.Center),
//                            tint = Color.White
//                        )
//                    }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /**
             * List of user info fields: name, email, phone, gender, birthdate.
             */
            ProfileField(label = "Full Name", value = name)
            ProfileField(label = "Email", value = email, icon = Icons.Default.Email)
            ProfileField(label = "Phone Number", value = phone, icon = Icons.Default.Phone)
            ProfileField(label = "Gender", value = gender, icon = Icons.Default.KeyboardArrowDown)
            ProfileField(label = "Birthdate", value = birthDate, icon = Icons.Default.DateRange)

            /**
             * Action buttons at the bottom: Edit (outlined) and Logout (filled).
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("editProfile") },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, Color.Green),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Green
                    )
                ) {
                    Text("Edit")
                }

                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Logout", color = Color.Black)
                }
            }
        }
    }
}

/**
 * Composable field showing a label and its associated value, optionally with an icon.
 *
 * @param label Label text, e.g., "Email"
 * @param value Value to display, e.g., "name@email.com"
 * @param icon Optional icon on the right side
 */
@Composable
fun ProfileField(label: String, value: String, icon: ImageVector? = null) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF8F8F8))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(value)
            icon?.let {
                Icon(imageVector = it, contentDescription = null)
            }
        }
    }
}
