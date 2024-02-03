package models.booking.airbnb

import kotlinx.serialization.Serializable

@Serializable
data class AirbnbData(
    val embedData: EmbedData?,
    val sharingConfig: SharingConfig?,
)