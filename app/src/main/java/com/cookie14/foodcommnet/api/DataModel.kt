package com.cookie14.foodcommnet.api

import com.cookie14.foodcommnet.ArticleModel
import io.reactivex.Single
import java.util.*

class DataModel {
    private val apiService: APIService = RetrofitManager().getAPI()

    //private val instance: DataModel = DataModel()

    //fun getInstance(): DataModel {
    //    return instance
    //}

    //測試用
    fun getListRepo(): Single<Resource<List<TestList>>> {
        return apiService.getList()
    }

    //取得文章列表
    fun getArticleListDataModel(): Single<Resource<List<ArticleModel>>> {
        return apiService.getArticleList()
    }
}