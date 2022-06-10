package com.batarilo.vinylcollectionPremium.data.model

import androidx.annotation.Keep

@Keep
data class Pagination(
    private val page:Int,
    private val pages: Int,

)