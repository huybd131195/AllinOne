package com.codefresher.allinone.model

import android.os.Parcelable
import java.io.Serializable

data class Users(
    val userId: String= "",
    val userName: String = "",
    val userAge: String ="",
    val userEmail: String = "",
    val url : String = ""
){}