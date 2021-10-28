package io.github.iromul.utils.stlunpacker

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import java.io.File
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.LinkOption.NOFOLLOW_LINKS
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.text.RegexOption.IGNORE_CASE

fun main(args: Array<String>) = object : CliktCommand(
    name = "stlunpacker",
    help = "Unpack and organize stl files from Thingiverse and others",
    printHelpOnEmptyArgs = true
) {
    private val source by argument().file(mustExist = true, canBeDir = false, mustBeReadable = true)

    override fun run() {
        val workingDir = Paths.get(".").toAbsolutePath().normalize().toFile()

        val outDirName = source.name.replace("+", " ").removeSuffix(".zip").trim()

        source.unzipHere(workingDir, outDirName)

        val modelDir = File(workingDir, outDirName)

        modelDir.walkTopDown()
            .onFail { file, ioException -> System.err.println("Path unreachable: ${file}\n\t$ioException") }
            .filter {
                it.name.matches(".*\\.(jpg|png)".toRegex(IGNORE_CASE))
            }
            .take(1)
            .forEach { imageFile ->
                File(modelDir, "desktop.ini").apply {
                    writeText("""
                        |[ViewState]
                        |FolderType=Generic
                        |Logo=${imageFile.absolutePath}
                        |
                    """.trimMargin("|"))

                    setAttribute("dos:hidden" to true, NOFOLLOW_LINKS)
                    setAttribute("dos:system" to true, NOFOLLOW_LINKS)
                }
            }
    }
}.main(args)

private fun File.unzipHere(workingDir: File, outName: String) {
    ProcessBuilder("7z", "x", "-o$outName", absolutePath)
        .directory(workingDir)
        .start()
        .also { it.waitFor(10, TimeUnit.SECONDS) }
}

private fun File.setAttribute(attribute: Pair<String, Any>, vararg options: LinkOption) {
    Files.setAttribute(toPath(), attribute.first, attribute.second, *options)
}