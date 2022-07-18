package com.cookie14.foodcomment.page2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cookie14.foodcomment.adapter.Page2ListAdapter
import com.cookie14.foodcomment.api.APIService
import com.cookie14.foodcomment.api.ApiManager
import com.cookie14.foodcomment.api.DataClass.ArticleModel
import com.cookie14.foodcomment.api.DataModel
import com.cookie14.foodcomment.api.Resource
import com.cookie14.foodcomment.databinding.ActivityListArticleBinding
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.ArithmeticException

class ListArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListArticleBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    val articleAdapter = Page2ListAdapter()
    private val disposable = CompositeDisposable()
    var articleList : List<ArticleModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout = binding.swipeRefreshLayout.also {
            it.setOnRefreshListener {
                initArticleUI()
            }
        }
        initArticleUI()
    }

    private fun initArticleUI() {
        val recycleView = binding.recycleView

        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycleView.layoutManager = linearLayoutManager
        recycleView.adapter = articleAdapter

        getArticleList()
        swipeRefreshLayout.isRefreshing = true
    }

    fun refreshArticleUI(){
        articleAdapter.submitList(articleList)
        swipeRefreshLayout.isRefreshing = false
    }

    fun getArticleList(){
        //開線程
        val requestJob = CoroutineScope(Dispatchers.IO).launch {
            //將DataModel放入寫好的函示，做try/catch
            val result = ApiManager.request(DataModel().getArticleList2DataModel())

            //處理好後回到主線程執行後續
            withContext(Dispatchers.Main){
                //檢查號碼，不是成功代號則為失敗
                if(result!!.code.toString() != "123"){
                    Toast.makeText(this@ListArticleActivity, result.code, Toast.LENGTH_SHORT).show()
                }

                //帶入表單，刷新介面
                articleList = result.data?.filterNotNull()
                refreshArticleUI()
            }
        }
        //取消
        //requestJob.takeIf { it.isActive }?.cancel()
    }

    /*fun getArticleList(){
        addDisposable(
            DataModel().getArticleListDataModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Resource<List<ArticleModel>>>(){
                    override fun onSuccess(t: Resource<List<ArticleModel>>) {
                        println(t.code)
                        println(t.data.toString())

                        articleList = t.data?.filterNotNull()
                        refreshArticleUI()
                    }
                    override fun onError(e: Throwable) {
                        println("ERRORRRRR:  $e")
                    }
                })
        )
    }*/

    protected fun addDisposable(d: Disposable) {
        disposable.add(d)
    }
}