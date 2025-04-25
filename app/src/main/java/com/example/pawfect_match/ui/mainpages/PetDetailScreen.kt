package com.example.pawfect_match.ui.mainpages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun PreviewPetDetailScreen() {
    PetDetailScreen(
        pet = PetDetails(
            name = "Mochi",
            breed = "Abyssinian",
            distance = "1.2 km",
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

@Composable
fun PetDetailScreen(pet: PetDetails, onBackClick: () -> Unit, onAdoptClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            // Header with back
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


            // Image block
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.LightGray)
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text("1 / 5", color = Color.White, modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(20))
                    .padding(horizontal = 12.dp, vertical = 4.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Pet name and breed
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(pet.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("(${pet.breed})", color = Color.Gray)
                }
                Text("\uD83D\uDCCD ${pet.distance}", color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                // Info chips
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    InfoChip("Gender", pet.gender, Color(0xFFFFEBEE), Modifier.weight(1f))
                    InfoChip("Age", pet.age, Color(0xFFF1F8E9), Modifier.weight(1f))
                    InfoChip("Size", pet.size, Color(0xFFE3F2FD), Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Shelter info
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


// Example data class
data class PetDetails(
    val name: String,
    val breed: String,
    val distance: String,
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
