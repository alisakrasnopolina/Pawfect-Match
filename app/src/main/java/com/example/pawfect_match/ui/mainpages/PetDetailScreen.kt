package com.example.pawfect_match.ui.mainpages

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
 * Preview version of the pet detail screen with dummy pet data.
 */
@Preview
@Composable
fun PreviewPetDetailScreen() {
    PetDetailScreen(
        pet = PetDetails(
            name = "Mochi",
            breed = "Abyssinian",
            distance = "1.2 km",
            imageUrl = "",
            gender = "Male",
            age = "Adult",
            size = "Medium",
            shelterName = "Happy Tails Animal Rescue",
            shelterAddress = "123 Paws Street, NYC, NY 10001",
            description = "Mochi is a stunning Abyssinian cat with a warm, golden coat...",
            traits = "Affectionate, Curious, Playful",
            idealHome = "A home with toys, sunshine and lots of love",
            adoptionInfo = "Contact the shelter at (555) 123-4567. Mochi is vaccinated and ready."
        ),
        onBackClick = {},
        onAdoptClick = {}
    )
}

/**
 * Detailed screen that displays information about a selected pet.
 *
 * @param pet The pet to be displayed in detail.
 * @param onBackClick Callback triggered when the back button is pressed.
 * @param onAdoptClick Callback triggered when the adopt button is pressed.
 */
@Composable
fun PetDetailScreen(pet: PetDetails, onBackClick: () -> Unit, onAdoptClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        /**
         * Scrollable column with pet content and shelter details.
         */
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            /**
             * Header with back arrow and centered title.
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Pet Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onBackClick() }
                )
            }


            /**
             * Pet image placeholder with position indicator.
             */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.LightGray)
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomCenter
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
                Text("1 / 5", color = Color.White, modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(20))
                    .padding(horizontal = 12.dp, vertical = 4.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                /**
                 * Pet name and breed.
                 */
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(pet.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("(${pet.breed})", color = Color.Gray)
                }
                Text("\uD83D\uDCCD ${pet.distance}", color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Info chips for gender, age, and size.
                 */
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    InfoChip("Gender", pet.gender, Color(0xFFFFEBEE), Modifier.weight(1f))
                    InfoChip("Age", pet.age, Color(0xFFF1F8E9), Modifier.weight(1f))
                    InfoChip("Size", pet.size, Color(0xFFE3F2FD), Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * Shelter information row with send icon.
                 */
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)) {}
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(pet.shelterName, fontWeight = FontWeight.SemiBold)
                        Text(pet.shelterAddress, fontSize = 12.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Navigate")
                }

                Spacer(modifier = Modifier.height(24.dp))

                /**
                 * Pet descriptive sections.
                 */
                Text("About ${pet.name}", fontWeight = FontWeight.Bold)
                Text(pet.description, lineHeight = 20.sp)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Personality Traits", fontWeight = FontWeight.Bold)
                Text(pet.traits, lineHeight = 20.sp)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Ideal Home", fontWeight = FontWeight.Bold)
                Text(pet.idealHome, lineHeight = 20.sp)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Adoption Information", fontWeight = FontWeight.Bold)
                Text(pet.adoptionInfo, lineHeight = 20.sp)

                Spacer(modifier = Modifier.height(80.dp))
            }

        }

        /**
         * Bottom row with favorite icon and adopt button.
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFF3E0))
                    .padding(12.dp),
                tint = Color.Green
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onAdoptClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("Adopt", color = Color.Black)
            }
        }
    }
}

/**
 * Composable displaying a small rounded info card with label and value.
 *
 * @param label Label of the information (e.g., Gender, Age).
 * @param value Value associated with the label.
 * @param backgroundColor Background color of the chip.
 * @param modifier Modifier for layout control (e.g., weight).
 */
@Composable
fun InfoChip(
    label: String,
    value: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(1.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold)
    }
}


/**
 * Data class containing all pet-related information for the detail screen.
 */
data class PetDetails(
    val name: String,
    val breed: String,
    val distance: String,
    val imageUrl: String,
    val gender: String,
    val age: String,
    val size: String,
    val shelterName: String,
    val shelterAddress: String,
    val description: String,
    val traits: String,
    val idealHome: String,
    val adoptionInfo: String
)
