package models.booking

import io.github.aakira.napier.Napier
import models.booking.airbnb.AirbnbData

data class BookingPreview(
    val site: BookingSite = BookingSite.UNKNOWN,
    val title: String?,
    val personCapacity: Int?,
    val nbBedroom: Int?,
    val starRating: Float?,
    val imageUrl: String?
) {
    companion object {
        fun fromAirbnb(airbnbData: AirbnbData): BookingPreview {
            val embedData = airbnbData.embedData
            Napier.e(airbnbData.sharingConfig?.title ?: "nop")
            val descriptions = airbnbData.sharingConfig?.title?.split(" · ")
            Napier.e("descriptions: $descriptions")
            val nbBedroom = descriptions?.firstOrNull { it.contains("chambres") }?.replace("chambres", "")?.trim()?.toIntOrNull()

            return BookingPreview(
                site = BookingSite.AIRBNB,
                title = embedData?.name,
                personCapacity = embedData?.personCapacity,
                nbBedroom = nbBedroom,
                starRating = embedData?.starRating,
                imageUrl = embedData?.pictureUrl
            )
        }

        fun fromLeboncoin(title: String?, personCapacity: Int?, nbBedroom: Int?, starRating: Float?, imageUrl: String?): BookingPreview {
            return BookingPreview(
                site = BookingSite.LEBONCOIN,
                title = title,
                personCapacity = personCapacity,
                nbBedroom = nbBedroom,
                starRating = starRating,
                imageUrl = imageUrl
            )
        }
    }

}