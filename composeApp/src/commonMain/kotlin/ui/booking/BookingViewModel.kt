package ui.booking

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequestBlocking
import com.fleeksoft.ksoup.nodes.Document
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import models.booking.BookingPreview
import models.booking.airbnb.AirbnbData
import moe.tlaster.precompose.viewmodel.ViewModel

private val json = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

class BookingViewModel() : ViewModel() {

    val bookingPreview: MutableState<BookingPreview?> = mutableStateOf(null)

    init {
        getBookingPreview()
    }

    fun getBookingPreview() {
        val airbnbUrl = "https://www.airbnb.fr/rooms/746222848659632957"

        // With Ksoup get the title, description and image url from the airbnb url
        val doc: Document = Ksoup.parseGetRequestBlocking(url = airbnbUrl)

        // Get Airbnb Sharing Config
        try {
            val docData = doc.data()
            val beginShareSave = docData.split("shareSave\":").firstOrNull { it.take(3).contains("{") } ?: ""
            var index = 0
            var countOpenBrackets = 0
            while ((index == 0 || countOpenBrackets != 0) && index < beginShareSave.length) {
                if (beginShareSave[index] == '{') {
                    countOpenBrackets++
                } else if (beginShareSave[index] == '}') {
                    countOpenBrackets--
                }
                index++
            }
            val shareSaveConfigStr = beginShareSave.substring(0, index)

            // Transform json data to AirbnbData with kotlinx.serialization
            Napier.e("ShareSaveConfigStr: $shareSaveConfigStr")
            val airbnbData: AirbnbData = json.decodeFromString(shareSaveConfigStr)

            bookingPreview.value = BookingPreview.fromAirbnbData(airbnbData)
        } catch (e: Exception) {
            Napier.e("Error while parsing Airbnb Sharing Config")
            Napier.e(e.message ?: "")
        }
    }
}