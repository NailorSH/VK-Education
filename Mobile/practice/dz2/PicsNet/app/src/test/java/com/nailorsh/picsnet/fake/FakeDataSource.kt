package com.nailorsh.picsnet.fake

import com.nailorsh.picsnet.network.PicsPhoto

object FakeDataSource {
    const val idOne = "img1"
    const val idTwo = "img2"
    const val imgOne = "url.1"
    const val imgTwo = "url.2"
    val photosList = listOf(
        PicsPhoto(
            id = idOne,
            imgSrc = imgOne
        ),
        PicsPhoto(
            id = idTwo,
            imgSrc = imgTwo
        )
    )
}