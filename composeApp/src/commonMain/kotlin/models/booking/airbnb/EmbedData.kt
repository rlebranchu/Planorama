package models.booking.airbnb

import kotlinx.serialization.Serializable

@Serializable
data class EmbedData(
    val id: String?,
    val name: String?,
    val personCapacity: Int?,
    val pictureUrl: String?,
    val propertyType: String?,
    val reviewCount: Int?,
    val starRating: Double?,
    val url: String?
)