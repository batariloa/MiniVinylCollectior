package com.batarilo.vinylcollection.data.model

data class PaginationX(
    val items: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val urls: Urls
)