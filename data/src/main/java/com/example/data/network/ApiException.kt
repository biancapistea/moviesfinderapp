package com.example.data.network

class ApiException(val noInternet: Boolean = false, override val message: String?) :
    RuntimeException()