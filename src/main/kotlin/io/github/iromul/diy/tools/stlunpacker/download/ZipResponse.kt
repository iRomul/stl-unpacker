package io.github.iromul.diy.tools.stlunpacker.download

data class ZipResponse(
    val url: String,
    val id: String,
    val data: ByteArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ZipResponse

        if (url != other.url) return false
        if (id != other.id) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}
