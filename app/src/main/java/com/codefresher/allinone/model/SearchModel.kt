package com.codefresher.allinone.model

data class SearchModel(
    var imageUrl: String,
    var title: String = "",
    var url: String = ""

) {
    constructor() : this("", "", "")
}