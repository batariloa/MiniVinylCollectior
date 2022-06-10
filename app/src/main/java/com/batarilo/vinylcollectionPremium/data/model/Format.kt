package com.batarilo.vinylcollectionPremium.data.model

import androidx.annotation.Keep

@Keep
data class Format(
    val descriptions: List<String>,
    val name: String,
    val qty: String
)