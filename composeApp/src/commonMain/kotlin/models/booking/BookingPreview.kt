package models.booking

import models.booking.airbnb.AirbnbData

data class BookingPreview(
    val title: String?,
    val description: String?,
    val imageUrl: String?
) {
    companion object {
        fun fromAirbnbData(airbnbData: AirbnbData): BookingPreview {
            val embedData = airbnbData.embedData
            return BookingPreview(
                title = airbnbData.sharingConfig?.title,
                description = "${embedData?.propertyType} - ${embedData?.personCapacity} guests - ${embedData?.reviewCount} reviews - ${embedData?.starRating} stars",
                imageUrl = embedData?.pictureUrl
            )
        }
    }

}