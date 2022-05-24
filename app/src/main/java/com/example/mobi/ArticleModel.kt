package com.example.mobi

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createdAt: Long,
    val contents: String,
    val imageUrl: String,
){

    constructor(): this("", "", 0, "", "")

}

