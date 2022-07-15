package com.cookie14.foodcomment.api

import com.cookie14.foodcomment.api.DataClass.ArticleModel
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @GET("/ajax_test2.php")
    fun getList(
        //@Field("username") username: String?,
        //@Field("password") password: String?
    ): Single<Resource<List<TestList>>>

    @GET("/foodcomment/getArticleList.php")
    fun getArticleList(): Single<Resource<List<ArticleModel>>>

    @POST("/foodcomment/setArticleList.php")
    @FormUrlEncoded
    fun setArticleList(
        @Field("doAction")doAction: String,
        @Field("title")title: String,
        @Field("img_res")img_res: String,
        @Field("location")location: String,
        @Field("price")price: Int
    ): Single<Resource<List<ArticleModel>>>
}