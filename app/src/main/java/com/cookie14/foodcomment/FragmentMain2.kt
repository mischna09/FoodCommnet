package com.cookie14.foodcomment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cookie14.foodcomment.api.DataClass.ArticleModel
import com.cookie14.foodcomment.api.DataModel
import com.cookie14.foodcomment.api.Resource
import com.cookie14.foodcomment.databinding.FragmentMain2Binding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FragmentMain2 : Fragment() {
    private val disposable = CompositeDisposable()
    lateinit var binding : FragmentMain2Binding
    lateinit var editTitle : EditText
    lateinit var editImgRes : EditText
    lateinit var editLocation : EditText
    lateinit var editPrice : EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMain2Binding.inflate(inflater, container, false)
        OnViewCreate()
        return binding.root
    }

    fun OnViewCreate(){

        editTitle = binding.editTitle
        editImgRes = binding.editImgres
        editLocation = binding.editLocation
        editPrice = binding.editPrice

        binding.btnSubmit.setOnClickListener{
            val dialog = AlertDialog.Builder(this.context)
                .setTitle("即將送出資料")
                .setMessage("請確認資料無誤後，按下確定送出。")
                .setNegativeButton("取消", null)
                .setPositiveButton("確定"){_,_ ->
                    setArticleList(
                        "ADD",
                        editTitle.text.toString(),
                        editImgRes.text.toString(),
                        editLocation.text.toString(),
                        editPrice.text.toString()
                    )
                }.create().show()
        }
    }

    fun setArticleList(doAction: String, title: String, img_res: String, location: String, price: String){
        //檢查空值
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(img_res) || TextUtils.isEmpty(location) || TextUtils.isEmpty(price)){
            Toast.makeText(this.context,"資料不能為空",Toast.LENGTH_SHORT).show()
            return
        }

        addDisposable(
            DataModel().setArticleListDataModel(doAction, title, img_res, location, price.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Resource<List<ArticleModel>>>(){
                    override fun onSuccess(t: Resource<List<ArticleModel>>) {
                        println(t.code)
                        println(t.data.toString())

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