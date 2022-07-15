package com.cookie14.foodcomment.api.DataClass

import com.google.gson.annotations.SerializedName

data class ArticleModel(
    @SerializedName("title")
    var title: String,
    @SerializedName("img_res")
    var img_res: String,
    @SerializedName("location")
    var location:String,
    @SerializedName("price")
    var price:Int,
)
