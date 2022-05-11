package com.batarilo.vinylcollection.data.model

data class JsonResponse(
    val pagination: PaginationX,

    val results: List<Record>
)