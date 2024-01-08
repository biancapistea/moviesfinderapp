package com.example.moviesfinderapp.ui.paginator

interface Paginator<Key, Item> {
    suspend fun loadNextPage()
    fun reset()
}