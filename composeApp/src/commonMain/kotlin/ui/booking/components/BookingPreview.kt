package ui.booking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import models.booking.BookingPreview

@Composable
fun BookingPreview(preview: BookingPreview) {
    Card(
        Modifier.fillMaxWidth().padding(16.dp).background(
            Color.Gray, RoundedCornerShape(8.dp)
        )
    ) {
        Column(Modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop,
                model = preview.imageUrl,
                contentDescription = "Image of the booking"
            )
            preview.title?.let { Text(it, color = Color.Black) }
            preview.description?.let { Text(it, color = Color.Black) }
        }
    }
}