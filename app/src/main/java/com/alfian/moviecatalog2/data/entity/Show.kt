package com.alfian.moviecatalog2.data.entity

data class Show(
    val id: String,
    val title: String?,
    val genre: String? = null,
    val description: String? = null,
    val imagePath: String?,
    val rating: Double? = null
    )