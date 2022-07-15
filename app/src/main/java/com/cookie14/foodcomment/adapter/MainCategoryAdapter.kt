package com.cookie14.foodcomment.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cookie14.foodcomment.databinding.ItemMainCategoryBinding

class MainCategoryAdapter() :
    ListAdapter<FoodCategoryModel, MainCategoryAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var binding : ItemMainCategoryBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //物件導向
        val viewHolder = ViewHolder(binding)
        context = parent.context
        viewHolder.text_title = binding.textTitle
        viewHolder.img_icon = binding.imgIcon
        viewHolder.layout_back = binding.layoutBack

        return viewHolder
    }

    /*override fun getItemCount(): Int {
        return data.size
    }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //設定個別物件
        val model = currentList[position]
        with(holder){
            //屬性
            text_title.text = model.title
            img_icon.setImageResource(model.img_res)
            layout_back.backgroundTintList = ColorStateList.valueOf(Color.parseColor(model.bg_color))
            //layout_back.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.main_orange_light))
            //事件
            layout_back.setOnClickListener{
                Toast.makeText(context,model.title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ViewHolder(val binding: ItemMainCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        //初始化物件
        lateinit var text_title : TextView
        lateinit var img_icon : ImageView
        lateinit var layout_back : ConstraintLayout
    }
}
//data class
data class FoodCategoryModel(
    var title: String,
    var img_res: Int,
    var bg_color:String,
)



//DiffUtil
class DiffCallback : DiffUtil.ItemCallback<FoodCategoryModel>() {
    override fun areItemsTheSame(oldItem: FoodCategoryModel, newItem: FoodCategoryModel) =
        oldItem.title === newItem.title

    override fun areContentsTheSame(oldItem: FoodCategoryModel, newItem: FoodCategoryModel) =
        oldItem.title == newItem.title
}