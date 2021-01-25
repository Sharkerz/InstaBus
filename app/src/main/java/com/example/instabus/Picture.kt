package com.example.instabus

import java.sql.Blob

data class Picture (
    val id: Int,
    val title: String,
    val image: ByteArray
        )