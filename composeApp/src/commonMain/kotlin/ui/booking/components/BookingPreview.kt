package ui.booking.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import models.booking.BookingPreview

@Composable
fun BookingPreview(preview: BookingPreview) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop,
                model = preview.imageUrl,
                contentDescription = "Image of the booking"
            )
            preview.title?.let { Text(it, color = Color.Black, fontWeight = FontWeight.Bold) }
            preview.personCapacity?.let { Text("$it personnes", color = Color.Black) }
            preview.nbBedroom?.let { Text("$it chambres", color = Color.Black) }
            preview.starRating?.let { Text("$it /5", color = Color.Black) }
        }
    }
}