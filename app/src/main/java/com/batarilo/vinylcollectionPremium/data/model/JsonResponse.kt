package com.batarilo.vinylcollectionPremium.data.model

import androidx.annotation.Keep

@Keep
data class JsonResponse(
    val pagination: PaginationX,

    val results: List<Record>
)