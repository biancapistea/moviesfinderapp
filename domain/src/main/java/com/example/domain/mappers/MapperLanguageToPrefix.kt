package com.example.domain.mappers

fun mapLanguageToPrefix(language: String): String {
    return when (language) {
        "All" -> {
            ""
        }

        "English" -> {
            "en"
        }

        "French" -> {
            "fr"
        }

        "Spanish" -> {
            "es"
        }

        "German" -> {
            "de"
        }

        "Italian" -> {
            "it"
        }

        "Hindi" -> {
            "hi"
        }

        "Romanian" -> {
            "ro"
        }

        else -> {
            ""
        }

    }
}

fun mapPrefixToLanguage(prefix: String): String {
    return when (prefix) {
        "en" -> {
            "English"
        }

        "fr" -> {
            "French"
        }

        "es" -> {
            "Spanish"
        }

        "de" -> {
            "German"
        }

        "it" -> {
            "Italian"
        }

        "hi" -> {
            "Hindi"
        }

        "ro" -> {
            "Romanian"
        }

        else -> {
            ""
        }

    }
}
