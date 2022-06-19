package io.github.iromul.diy.tools.stlunpacker.download

import io.github.iromul.diy.tools.stlunpacker.UnpackServiceException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.jsoup.Jsoup

class ThingiverseClient {

    private val client by lazy { HttpClient() }

    fun isAppropriateUrl(url: String): Boolean {
        return url.contains(thingIdRegex)
    }

    fun getThingId(url: String): String {
        val match = thingIdRegex.find(url)

        requireNotNull(match)

        return match.groupValues[1]
    }

    fun downloadThingById(id: String): ZipResponse {
        return runBlocking {
            val url = thingZipById(id)

            logger.debug { "Downloading zip from $url" }

            val response = client.get(url)

            if (response.status.isSuccess() && response.contentType()!!.match("application/zip")) {
                val actualRequestUrl = response.request.url.toURI().toASCIIString()

                logger.debug { "Successfully downloaded (status=${response.status.value}, url=$actualRequestUrl)" }

                ZipResponse(
                    actualRequestUrl,
                    id,
                    response.body()
                )
            } else {
                logger.error { "Bad status or content type (status=${response.status.value}, contentType=${response.contentType()?.contentType})" }

                throw UnpackServiceException("Can't connect to Thingiverse")
            }
        }
    }

    fun getDescriptionById(id: String): MetaResponse {
        return runBlocking {
            val url = thingDescriptionById(id)

            logger.debug { "Getting page from $url" }

            val response = client.get(url)

            if (response.status.isSuccess() && response.contentType()!!.match("text/html")) {
                val page = response.body<String>()

                val document = Jsoup.parse(page)

                val metaTitle = document.getElementsByAttributeValue("property", "og:title")

                val metaTitleValue = metaTitle.attr("content")

                val metaDescription = document.getElementsByAttributeValue("property", "og:description")

                val metaDescriptionValue = metaDescription.attr("content")

                MetaResponse(
                    url,
                    metaTitleValue,
                    metaDescriptionValue
                )
            } else {
                logger.error { "Bad status or content type (status=${response.status.value}, contentType=${response.contentType()?.contentType})" }

                throw UnpackServiceException("Can't connect to Thingiverse")
            }
        }
    }

    companion object : KLogging() {

        private const val ROOT = "www.thingiverse.com"
        private const val HOST = "https://$ROOT"

        private val thingIdRegex = "$ROOT/thing:(\\d+)".toRegex()

        private fun thingZipById(id: String) = "$HOST/thing:$id/zip"
        private fun thingDescriptionById(id: String) = "$HOST/thing:$id"
    }
}