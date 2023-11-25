package com.nailorsh.picsnet.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PicsPhoto(
    val id: String,
    @SerialName(value = "img_src") val imgSrc: String
)
