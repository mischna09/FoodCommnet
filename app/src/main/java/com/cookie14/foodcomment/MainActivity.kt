package com.cookie14.foodcomment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cookie14.foodcomment.adapter.MainViewAdapter
import com.cookie14.foodcomment.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    val presenter = MainPresenter(this)
    val viewAdapter = MainViewAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPagerInit()

        //Tab切換
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun viewPagerInit() {
        val fragmentList: ArrayList<Fragment> = ArrayList()
        fragmentList.add(FragmentMainMain())
        fragmentList.add(FragmentMain2())

        viewAdapter.updateList(fragmentList)
        viewPager = binding.viewpager
        viewPager.adapter = viewAdapter

        viewPager.isUserInputEnabled = false

    }

    override fun refreshArticleUI() {

    }


}