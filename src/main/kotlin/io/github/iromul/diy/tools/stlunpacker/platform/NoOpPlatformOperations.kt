package io.github.iromul.diy.tools.stlunpacker.platform

import java.io.File

class NoOpPlatformOperations : PlatformOperations {

    override fun readClipboardText(): String? {
        // no-op

        return null
    }

    override fun notify(caption: String, text: String) {
        // no-op
    }

    override fun createThumbnail(modelDir: File) {
        // no-op
    }

    override fun unzipHere(zipFile: File, targetDir: File, dirName: String) {
        // no-op
    }
}