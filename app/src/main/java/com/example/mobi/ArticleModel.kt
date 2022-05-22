package com.example.mobi

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createdAt: Long,
    //나중에 가격 말고 내용으로 변경
    val price: String,
    val imageUrl: String,
){

    constructor(): this("", "", 0, "", "")

}

