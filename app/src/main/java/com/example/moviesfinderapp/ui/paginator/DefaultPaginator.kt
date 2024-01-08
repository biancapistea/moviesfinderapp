package com.example.moviesfinderapp.ui.paginator

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: (nextKey: Key) -> Unit
) : Paginator<Key, Item> {
    private var currentKey = initialKey
    private var isMakingRequest = false


    override suspend fun loadNextPage() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        onRequest(currentKey)
        onLoadUpdated(false)
        isMakingRequest = false
    }

    fun setNextPage( nextKey: Key) {
        currentKey = nextKey
    }

    override fun reset() {
        currentKey = initialKey
    }
}