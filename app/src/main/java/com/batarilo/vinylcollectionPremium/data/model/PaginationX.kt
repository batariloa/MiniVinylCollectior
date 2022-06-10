package com.batarilo.vinylcollectionPremium.data.model

import androidx.annotation.Keep

@Keep
data class PaginationX(
    val items: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val urls: Urls
)