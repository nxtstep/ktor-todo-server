package io.supersimple.common.security

import java.security.MessageDigest

enum class Hashing {
    SHA256;

    fun hash(value: String): String = when(this) {
        SHA256 -> hashString(value, "SHA-256")
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest.getInstance(algorithm)
            .digest(input.toByteArray())
            .hexString()
    }
}

fun ByteArray.hexString(): String = fold("") { str, it -> str + "%02x".format(it) }