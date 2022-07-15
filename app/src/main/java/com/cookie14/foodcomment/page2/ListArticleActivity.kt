package com.cookie14.foodcomment.page2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cookie14.foodcomment.adapter.MainArticleAdapter
import com.cookie14.foodcomment.api.DataClass.ArticleModel
import com.cookie14.foodcomment.api.DataModel
import com.cookie14.foodcomment.api.Resource
import com.cookie14.foodcomment.databinding.ActivityListArticleBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListArticleBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    val articleAdapter = MainArticleAdapter()
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
    }

    protected fun addDisposable(d: Disposable) {
        disposable.add(d)
    }
}