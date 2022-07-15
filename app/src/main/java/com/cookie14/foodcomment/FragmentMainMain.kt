package com.cookie14.foodcomment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.cookie14.foodcomment.adapter.FoodCategoryModel
import com.cookie14.foodcomment.adapter.MainArticleAdapter
import com.cookie14.foodcomment.adapter.MainCarouselAdapter
import com.cookie14.foodcomment.adapter.MainCategoryAdapter
import com.cookie14.foodcomment.api.DataClass.ArticleModel
import com.cookie14.foodcomment.api.DataModel
import com.cookie14.foodcomment.api.Resource
import com.cookie14.foodcomment.databinding.FragmentMainMainBinding
import com.cookie14.foodcomment.page2.ListArticleActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FragmentMainMain : Fragment() {
    lateinit var binding : FragmentMainMainBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewpager: ViewPager2
    val articleAdapter = MainArticleAdapter()
    var carouselAdapter : MainCarouselAdapter? = null
    private val disposable = CompositeDisposable()
    var articleList : List<ArticleModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMainBinding.inflate(inflater, container, false)
        OnViewCreate()
        return binding.root
    }

    fun OnViewCreate(){
        swipeRefreshLayout = binding.swipeRefreshLayout.also {
            it.setOnRefreshListener {
                initFoodCategoryUI()
                initArticleUI()
            }
        }
        viewpager = binding.viewpager
        initUI()

        binding.btnCategorySeeall.setOnClickListener{
            //presenter.testRetrofit()
        }

        binding.btnArticleSeeall.setOnClickListener{
            val intent = Intent(activity, ListArticleActivity::class.java);
            activity?.startActivity(intent);
        }

    }
    private fun initUI() {
        initFoodCategoryUI()
        initArticleUI()
        initCarouselUI()
    }

    private fun initCarouselUI() {
        carouselAdapter = MainCarouselAdapter(this.requireActivity())
        binding.viewpager.adapter = carouselAdapter

        val disposable: Disposable = Observable.interval(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { a ->
                carouselChange()
            }
    }

    private fun carouselChange(){
        if(viewpager.currentItem+1 < carouselAdapter!!.itemCount){
            viewpager.setCurrentItem(viewpager.currentItem+1, true)
        }else{
            viewpager.setCurrentItem(0, true)
        }
    }

    private fun initFoodCategoryUI() {
        val categoryList = listOf<FoodCategoryModel>(
            FoodCategoryModel("漢堡", R.drawable.ic_baseline_fastfood_24, "#F2B5B0"),
            FoodCategoryModel("餅乾", R.drawable.ic_baseline_cookie_24, "#75C6DD"),
            FoodCategoryModel("簡餐", R.drawable.ic_baseline_flatware_24, "#ECA370"),
            FoodCategoryModel("咖啡", R.drawable.ic_baseline_local_cafe_24, "#B8A49F"),
        )
        val recycleView = binding.recycleviewCategory
        val adapter = MainCategoryAdapter()
        val linearLayoutManager = LinearLayoutManager(this.context)

        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycleView.layoutManager = linearLayoutManager

        adapter.submitList(categoryList)
        recycleView.adapter = adapter
    }

    private fun initArticleUI() {
        val recycleView = binding.recycleviewArticle

        val linearLayoutManager = LinearLayoutManager(this.context)

        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
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