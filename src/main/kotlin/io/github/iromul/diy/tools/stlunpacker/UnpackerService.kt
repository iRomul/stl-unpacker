package io.github.iromul.diy.tools.stlunpacker

import io.github.iromul.diy.tools.stlunpacker.download.ThingiverseClient
import io.github.iromul.diy.tools.stlunpacker.platform.PlatformOperations
import mu.KLogging
import java.io.File

class UnpackerService(
    private val platformOperations: PlatformOperations,
    private val thingiverseClient: ThingiverseClient
) {

    fun unpackArchive(workingDir: File, source: File): File {
        val outDirName = source.name
            .replace("+", " ")
            .replace("_", " ")
            .removeSuffix(".zip")
            .trim()

        val modelDir = File(workingDir, outDirName)

        if (modelDir.exists()) {
            if (!modelDir.isDirectory) {
                throw UnpackServiceException("Target file exists and it is not a directory")
            }

            modelDir.deleteRecursively()
        }

        platformOperations.unzipHere(source, workingDir, outDirName)

        platformOperations.createThumbnail(modelDir)

        return modelDir
    }

    fun downloadAndUnpackArchive(targetDir: File) {
        val clipboardText = platformOperations.readClipboardText()

        logger.debug { "Clipboard text: $clipboardText" }

        if (clipboardText == null || !thingiverseClient.isAppropriateUrl(clipboardText)) {
            platformOperations.notify("STL Downloader", "No acceptable URL was found in clipboard")

            return
        }

        val id = thingiverseClient.getThingId(clipboardText)

        val response = thingiverseClient.downloadThingById(id)
        val description = thingiverseClient.getDescriptionById(id)

        val tempArchiveFileName =
            if (response.url.endsWith(".zip")) {
                val archiveName = response.url.split("/").last()
                val actualName =
                    if (archiveName.matches("\\d+\\.zip".toRegex())) {
                        "${description.thingTitle}.zip"
                    } else {
                        archiveName
                    }

                actualName
            } else {
                "${description.thingTitle}.zip"
            }

        logger.debug { "Temporary archive name: $tempArchiveFileName" }

        val tmpFile = File(targetDir, tempArchiveFileName)

        tmpFile.writeBytes(response.data)

        platformOperations.notify("STL Downloader", "File $tempArchiveFileName successfully downloaded")

        val modelDir = unpackArchive(targetDir, tmpFile)

        tmpFile.delete()

        File(modelDir, "Description.txt").writeText(
            """
            |${description.thingPage}
            |
            |${description.thingTitle}
            |
            |${description.thingDescription}
        """.trimMargin()
        )

        logger.debug { "Temporary archive removed" }
    }

    companion object : KLogging()
}