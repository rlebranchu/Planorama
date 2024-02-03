package models.booking.airbnb

import kotlinx.serialization.Serializable

@Serializable
data class SharingConfig(
    val title: String?
)