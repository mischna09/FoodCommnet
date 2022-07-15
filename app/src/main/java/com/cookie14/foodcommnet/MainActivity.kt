package com.cookie14.foodcommnet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookie14.foodcommnet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val presenter = MainPresenter(this)

        initFoodCategoryUI()
        initArticleUI()
        binding.btnCategorySeeall.setOnClickListener{
            println("PRESS")
            presenter.testRetrofit()
        }
    }

    private fun initFoodCategoryUI() {
        val categoryList = listOf<FoodCategoryModel>(   //背景顏色 要加到data class裡面
            FoodCategoryModel("漢堡", R.drawable.ic_baseline_fastfood_24, "#F2B5B0"),
            FoodCategoryModel("餅乾", R.drawable.ic_baseline_cookie_24, "#75C6DD"),
            FoodCategoryModel("簡餐", R.drawable.ic_baseline_flatware_24, "#ECA370"),
            FoodCategoryModel("咖啡", R.drawable.ic_baseline_local_cafe_24, "#B8A49F"),
        )
        val recycleView = binding.recycleviewCategory
        val adapter = MainCategoryAdapter()
        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycleView.layoutManager = linearLayoutManager

        adapter.submitList(categoryList)
        recycleView.adapter = adapter
    }

    private fun initArticleUI() {
        val categoryList = listOf<ArticleModel>(   //背景顏色 要加到data class裡面
            ArticleModel("大麥克套餐", R.drawable.bigmac, "麥當勞 中華店", 129),
            ArticleModel("麥香雞套餐", R.drawable.chi, "麥當勞 中華店", 99),
            ArticleModel("起司華堡套餐", R.drawable.bur, "漢堡王 中華店", 189),
        )
        val recycleView = binding.recycleviewArticle
        val adapter = MainArticleAdapter()
        val linearLayoutManager = LinearLayoutManager(this)

        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycleView.layoutManager = linearLayoutManager

        adapter.submitList(categoryList)
        recycleView.adapter = adapter
    }
}