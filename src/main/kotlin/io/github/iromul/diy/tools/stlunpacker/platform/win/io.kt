package io.github.iromul.diy.tools.stlunpacker.platform.win

import java.io.File
import java.nio.file.Files
import java.nio.file.LinkOption

fun File.setAttribute(attribute: Pair<String, Any>, vararg options: LinkOption) {
    Files.setAttribute(toPath(), attribute.first, attribute.second, *options)
}