package services

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequestBlocking
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.parser.Parser
import io.github.aakira.napier.Napier
import io.ktor.client.request.header
import kotlinx.serialization.json.Json
import models.booking.BookingPreview
import models.booking.airbnb.AirbnbData

class BookingService {

    fun getAirbnbBookingPreview(airbnbUrl: String): BookingPreview? {

        // With Ksoup get the title, description and image url from the airbnb url
        val doc: Document = Ksoup.parseGetRequestBlocking(
            url = airbnbUrl,
            httpRequestBuilder = {
                this.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0")
            })

        // Get Airbnb Sharing Config
        return try {
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
            val airbnbData: AirbnbData = Json {
                isLenient = true
                ignoreUnknownKeys = true
            }.decodeFromString(shareSaveConfigStr)
            BookingPreview.fromAirbnb(airbnbData)
        } catch (e: Exception) {
            Napier.e("Error while parsing Airbnb Sharing Config")
            Napier.e(e.message ?: "")
            null
        }
    }

    fun getLeboncoinBookingPreview(leboncoinUrl: String): BookingPreview? {

        // With Ksoup get the title, description and image url from the leboncoin url
        val doc: Document = Ksoup.parseGetRequestBlocking(
            url = leboncoinUrl,
            httpRequestBuilder = {
                this.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0")
            }
        )

        // Get Leboncoin
        return try {
            val mainDataElementGrid = doc.getElementById("grid")
            val articleData = mainDataElementGrid?.getElementsByTag("article")
                ?.firstOrNull {
                    it.children().any {
                        // We find the first article which has a child
                        //    with the attribute data-qa-id="adview_spotlight_container"
                        it.getElementsByAttributeValue("data-qa-id", "adview_spotlight_container").isNotEmpty()
                    }
                }

            // Picture
            val sectionPictures = articleData?.getElementsByAttributeValue("data-qa-id", "adview_spotlight_container")?.firstOrNull()
            val mainPicture = sectionPictures?.getAllElements()?.firstOrNull {
                it.attr("alt").contains("(image 1)")
                        && it.elementIs("img", Parser.NamespaceHtml)
            }

            val titleElement = articleData?.getElementsByAttributeValue("data-qa-id", "adview_title")?.firstOrNull()

            val bookingData = titleElement?.parent()?.select("span")

            var personCapacity: Int? = null
            var nbBedroom: Int? = null

            bookingData?.forEach {
                if (it.text().contains("personnes", ignoreCase = true)) {
                    personCapacity = it.text().split(" ").first().toInt()
                }
                if (it.text().contains("chambres", ignoreCase = true)) {
                    nbBedroom = it.text().split(" ").first().toInt()
                }
            }

            BookingPreview.fromLeboncoin(
                title = titleElement?.text(),
                personCapacity = personCapacity,
                nbBedroom = nbBedroom,
                starRating = 4.6f,
                imageUrl = mainPicture?.attr("src")
            )
        } catch (e: Exception) {
            Napier.e("Error while parsing Airbnb Sharing Config")
            Napier.e(e.message ?: "")
            null
        }
    }
}