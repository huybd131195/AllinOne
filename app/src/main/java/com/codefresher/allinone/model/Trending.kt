package com.codefresher.allinone.model

import android.os.Parcelable
import java.io.Serializable

data class Trending(
    var imageUrl: String,
    var title: String = "",
    var url: String = ""

) {
    constructor() : this("","","")
}