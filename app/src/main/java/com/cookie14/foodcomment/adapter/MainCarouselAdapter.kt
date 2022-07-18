package com.cookie14.foodcomment.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cookie14.foodcomment.R
import com.cookie14.foodcomment.databinding.LayoutMainCurouselBinding

class MainCarouselAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3;
    }
    override fun createFragment(position: Int): Fragment {
        return ViewPagerFragment(position)
    }
}
class ViewPagerFragment(): Fragment() {

    private var position: Int = 0
    private lateinit var binding: LayoutMainCurouselBinding

    constructor(position: Int) : this() {
        this.position = position
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutMainCurouselBinding.inflate(inflater,container,false)

        binding.run {
            when(position){
                0 ->    imgMain.setImageResource(R.drawable.bur)
                1 ->    imgMain.setImageResource(R.drawable.bigmac)
                2 ->    imgMain.setImageResource(R.drawable.chi)
            }
        }


        return binding.root
    }
}