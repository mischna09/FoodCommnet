package com.cookie14.foodcomment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var list: ArrayList<Fragment> = ArrayList()
    fun updateList(list: ArrayList<Fragment>) {
        this.list = list
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}
