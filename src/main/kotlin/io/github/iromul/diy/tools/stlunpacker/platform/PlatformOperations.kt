package io.github.iromul.diy.tools.stlunpacker.platform

import java.io.File

interface PlatformOperations {

    fun readClipboardText(): String?
    fun notify(caption: String, text: String)

    fun createThumbnail(modelDir: File)
    fun unzipHere(zipFile: File, targetDir: File, dirName: String)
}