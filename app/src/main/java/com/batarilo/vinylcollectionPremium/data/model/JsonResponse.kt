package com.batarilo.vinylcollectionPremium.data.model

data class JsonResponse(
    val pagination: PaginationX,

    val results: List<Record>
)