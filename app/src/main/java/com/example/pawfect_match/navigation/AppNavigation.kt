package com.example.pawfect_match.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pawfect_match.data.firebase.FirebaseAuthManager
import com.example.pawfect_match.data.model.Pet
import com.example.pawfect_match.data.model.User
import com.example.pawfect_match.viewmodel.AuthViewModel
import com.example.pawfect_match.viewmodel.UserViewModel
import com.example.pawfect_match.ui.mainpages.*
import com.example.pawfect_match.ui.registration.LogInScreen
import com.example.pawfect_match.ui.registration.SignUpScreen
import com.example.pawfect_match.ui.welcomepages.StepFour_FinalProfile
import com.example.pawfect_match.ui.welcomepages.StepOne_SelectUserType
import com.example.pawfect_match.ui.welcomepages.StepTwo_SelectAnimalType
import com.example.pawfect_match.viewmodel.FavoritesViewModel
//import com.example.pawfect_match.viewmodel.FavoritesViewModel
import com.example.pawfect_match.viewmodel.PetfinderViewModel

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = "signup") {
    val authViewModel: AuthViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val petfinderViewModel: PetfinderViewModel = viewModel()
    val favoritesViewModel: FavoritesViewModel = viewModel()
    var startDestination = if (FirebaseAuthManager.currentUser() == null) "signup" else "home"



    NavHost(navController = navController, startDestination = startDestination) {

        composable("signup") {
            SignUpScreen(navController = navController, viewModel = authViewModel)
        }

        composable("login") {
            authViewModel.resetAuthState()
            LogInScreen(navController = navController, viewModel = authViewModel)
        }

        composable("onboarding1") {
            StepOne_SelectUserType(navController, userViewModel)
        }

        composable("onboarding2") {
            StepTwo_SelectAnimalType(navController, userViewModel)
        }

        composable("onboarding3") {
            StepFour_FinalProfile(navController, userViewModel)
        }

        composable("home") {
            HomeScreen(navController = navController, userViewModel, petfinderViewModel, favoritesViewModel)
        }

        composable("filters") {
            PetSearchFilterScreen(navController = navController)
        }

        composable("results") {
//            val pets by petfinderViewModel.pets.collectAsState()
            val selectedType by remember { mutableStateOf("Dogs") }

            SearchResultsScreen(
                navController = navController,
                petfinderViewModel,
                favoritesViewModel,
                selectedType = selectedType,
                onTypeSelected = { newType ->
                    petfinderViewModel.loadPetsByType(newType)
                }
            )
        }

        composable("favorites") {
            FavoritesScreen(
                navController = navController,
                viewModel = favoritesViewModel,
                petfinderViewModel,
                onPetClick = { pet ->
                    petfinderViewModel.setSelectedPet(pet)
                    navController.navigate("petDetail")
                }
            )
        }

        composable("account") {
            AccountScreen(
                navController = navController,
                userViewModel = userViewModel,
                onLogout = {
                FirebaseAuthManager.logout()
                authViewModel.resetAuthState()
                navController.navigate("signup") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            })
        }

        composable("editProfile") {
            val user by userViewModel.user.collectAsState()

            EditProfileScreen(navController = navController,
                userViewModel = userViewModel,
                currentName = user?.name.orEmpty(),
                currentEmail = user?.email.orEmpty(),
                currentPhone = user?.phone.orEmpty(),
                currentGender = user?.gender.orEmpty(),
                currentBirthdate = user?.birthdate.orEmpty(),
                currentProfileUrl = user?.profileImageUrl.orEmpty(),
                onSave = { name, email, phone, gender, birthdate, image ->
                    val updated = User(
                        uid = FirebaseAuthManager.currentUser()?.uid,
                        name = name,
                        email = email,
                        phone = phone,
                        gender = gender,
                        birthdate = birthdate,
                        profileImageUrl = image
                    )
                    userViewModel.updateUser(updated)
                    navController.popBackStack()
                },
            )
        }

        composable("petDetail") {
            PetDetailScreen(
                navController,
                favoritesViewModel,
                petfinderViewModel
            )
        }

    }
//    NavHost(navController = navController, startDestination = startDestination) {
//
//        // Account Screen
//        composable("account") {
//            AccountScreen(
//                navController = navController,
//                userViewModel = userViewModel
//            )
//        }
//
//        // Edit Profile Screen
//        composable("editProfile") {
//            val user = userState ?: return@composable
//            EditProfileScreen(
//                currentName = user.name.orEmpty(),
//                currentEmail = user.email.orEmpty(),
//                currentPhone = user.phone.orEmpty(),
//                currentGender = user.gender.orEmpty(),
//                currentBirthdate = user.birthdate.orEmpty(),
//                currentProfileUrl = user.profileImageUrl.orEmpty(),
//                onSave = { name, email, phone, gender, birthdate, imageUrl ->
//                    userViewModel.updateUser(
//                        user.copy(
//                            name = name,
//                            email = email,
//                            phone = phone,
//                            gender = gender,
//                            birthdate = birthdate,
//                            profileImageUrl = imageUrl
//                        )
//                    )
//                    navController.popBackStack()
//                },
//                onBack = { navController.popBackStack() }
//            )
//        }
//    }
}
