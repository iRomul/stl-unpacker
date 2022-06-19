package io.github.iromul.diy.tools.stlunpacker

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file

val unpackerService = ApplicationContext.unpackerService

class RootCommand : NoOpCliktCommand(
    name = "stlunpacker",
    printHelpOnEmptyArgs = true
)

class UnpackHereCommand : CliktCommand(
    name = "unpack",
    help = "Unpack and organize stl files from Thingiverse and others",
    printHelpOnEmptyArgs = true
) {

    private val source by argument(name = "SOURCE")
        .file(mustExist = true, canBeDir = false, mustBeReadable = true)

    private val target by argument(name = "TARGET")
        .file(mustExist = false, canBeFile = false, mustBeWritable = true)

    override fun run() {
        unpackerService.unpackArchive(target, source)
    }
}

class DownloadCommand : CliktCommand(
    name = "download",
    help = "Download and organize from Thingiverse and others",
    printHelpOnEmptyArgs = true
) {

    private val target by argument(name = "TARGET")
        .file(mustExist = false, canBeFile = false, mustBeWritable = true)

    override fun run() {
        unpackerService.downloadAndUnpackArchive(target)
    }
}

fun main(args: Array<String>) =
    RootCommand()
        .subcommands(UnpackHereCommand(), DownloadCommand())
        .main(args)
