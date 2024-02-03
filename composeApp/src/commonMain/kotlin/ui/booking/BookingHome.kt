package ui.booking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.koin.koinViewModel
import ui.booking.components.BookingPreview

@Composable
fun BookingHome() {
    val viewModel = koinViewModel(BookingViewModel::class)

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        viewModel.bookingPreview.value?.let {
            BookingPreview(it)
        }
    }
}