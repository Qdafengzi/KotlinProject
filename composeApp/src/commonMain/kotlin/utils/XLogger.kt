package utils

import io.github.aakira.napier.Napier

object XLogger {
    fun i(message: String, tag: String? = null) {
        Napier.i(message = message, tag = tag)
    }

    fun d(message: String, tag: String? = null) {
        Napier.d(message = message, tag = tag)
    }

    fun e(message: String, tag: String? = null) {
        Napier.e(message = message, tag = tag)
    }

    fun w(message: String, tag: String? = null) {
        Napier.w(message = message, tag = tag)
    }
}