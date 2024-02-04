package ui.booking

import io.ktor.http.Url
import models.booking.BookingPreview
import moe.tlaster.precompose.viewmodel.ViewModel
import services.BookingService

class BookingViewModel(
    private val bookingService: BookingService
) : ViewModel() {

    val bookingPreviews: MutableList<BookingPreview> = mutableListOf()

    init {
        getBookingPreview()
    }

    private fun getBookingPreview() {
        val bookingUrls = listOf(
            "https://www.airbnb.fr/rooms/746222848659632957",
            "https://www.leboncoin.fr/offre/locations_saisonnieres/2078084135"
        )

        bookingPreviews.addAll(bookingUrls.mapNotNull { url ->
            val netUrl = Url(url)
            val host = netUrl.host
            when {
                host.contains("airbnb") -> bookingService.getAirbnbBookingPreview(url)
                host.contains("leboncoin") -> bookingService.getLeboncoinBookingPreview(url)
                else -> null
            }
        })
    }
}