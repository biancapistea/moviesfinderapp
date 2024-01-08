package com.example.data.network

class AppConfiguration {
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val accessToken =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1OTc0NzBjYTliNzg1MGM5NzdlMzk4ZGZmNTMyN2UwOCIsInN1YiI6IjY1NjQ2MWM3YjIzNGI5MDBmZjI0NDE5NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.isfRZbkgBHiyDNDnSNMoPL47lY0fWb71wWa1njkrm80"

    fun getBaseUrl(): String {
        return baseUrl
    }

    fun getAccessToken(): String {
        return accessToken
    }
}