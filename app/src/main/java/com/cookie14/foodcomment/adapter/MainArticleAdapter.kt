package com.cookie14.foodcomment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cookie14.foodcomment.api.DataClass.ArticleModel
import com.cookie14.foodcomment.databinding.ItemMainArticle2Binding
import com.cookie14.foodcomment.databinding.ItemMainArticleBinding

class MainArticleAdapter(private var edition: Int=0) :
    ListAdapter<ArticleModel, MainArticleAdapter.ViewHolder>(DiffCallback2()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemMainArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //物件導向
        val viewHolder = ViewHolder(binding)
        context = parent.context
        viewHolder.text_title = binding.textTitle
        viewHolder.text_location = binding.textLocation
        viewHolder.text_price = binding.textPrice
        viewHolder.img_icon = binding.imgPhoto
        viewHolder.layout_back = binding.layoutBack

        return viewHolder
    }
    /*override fun getItemCount(): Int {
        return data.size
    }*/

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //設定個別物件
        val model = currentList[position]
        with(holder) {
            //屬性
            text_title.text = model.title
            img_icon.setImageResource(
                context.resources.getIdentifier(
                    model.img_res,
                    "drawable",
                    context.packageName
                )
            )
            text_location.text = model.location
            text_price.text = "$${model.price}元"
            //事件
            layout_back.setOnClickListener {
                Toast.makeText(context, model.title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ViewHolder(val binding: ItemMainArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        //初始化物件
        lateinit var text_title: TextView
        lateinit var text_location: TextView
        lateinit var text_price: TextView
        lateinit var img_icon: ImageView
        lateinit var layout_back: ConstraintLayout
    }
}

//DiffUtil
class DiffCallback2 : DiffUtil.ItemCallback<ArticleModel>() {
    override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel) =
        oldItem.title === newItem.title

    override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel) =
        oldItem.title == newItem.title
}