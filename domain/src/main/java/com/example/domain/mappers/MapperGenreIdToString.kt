package com.example.domain.mappers

fun mapGenreIdToString(id: Int): String {
    return when (id) {
        28 -> "Action"
        12 -> "Adventure"
        16 -> "Animation"
        35 -> "Comedy"
        80 -> "Crime"
        99 -> "Documentary"
        18 -> "Drama"
        10751 -> "Family"
        14 -> "Fantasy"
        36 -> "History"
        27 -> "Horror"
        10402 -> "Musical"
        9648 -> "Mystery"
        10749 -> "Romance"
        878 -> "SF"
        10770 -> "TV Movie"
        53 -> "Thriller"
        10752 -> "War"
        37 -> "Western"
        else -> {
            ""
        }
    }
}

fun mapGenreStringToId(genre: String): Int {
    return when (genre) {
        "Action" -> 28
        "Adventure" -> 12
        "Animation" -> 16
        "Comedy" -> 35
        "Crime" -> 80
        "Documentary" -> 99
        "Drama" -> 18
        "Family" -> 10751
        "Fantasy" -> 14
        "History" -> 36
        "Horror" -> 27
        "Musical" -> 10402
        "Mystery" -> 9648
        "Romance" -> 10749
        "SF" -> 878
        "TV Movie" -> 10770
        "Thriller" -> 53
        "War" -> 10752
        "Western" -> 37
        else -> {
            0
        }
    }
}