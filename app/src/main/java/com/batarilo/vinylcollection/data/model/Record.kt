package com.batarilo.vinylcollection.data.model

data class Record(
    val barcode: List<String>,
    val catno: String,
    val community: Community,
    val country: String,
    val cover_image: String,
    val format: List<String>,
    val format_quantity: Int,
    val formats: List<Format>,
    val genre: List<String>,
    val id: Int,
    val label: List<String>,
    val master_id: Int,
    val master_url: Any,
    val resource_url: String,
    val style: List<String>,
    val thumb: String,
    val title: String,
    val type: String,
    val uri: String,
    val year: String
)