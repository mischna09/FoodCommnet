package com.cookie14.foodcomment.api

import com.cookie14.foodcomment.api.DataClass.ArticleModel
import io.reactivex.Single

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

    //取得文章列表
    fun setArticleListDataModel(doAction: String, title: String, img_res: String, location: String, price: Int): Single<Resource<List<ArticleModel>>> {
        return apiService.setArticleList(doAction, title, img_res, location, price)
    }
}