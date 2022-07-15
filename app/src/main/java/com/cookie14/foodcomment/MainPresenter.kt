package com.cookie14.foodcomment

import com.cookie14.foodcomment.api.DataClass.ArticleModel
import com.cookie14.foodcomment.api.DataModel
import com.cookie14.foodcomment.api.Resource
import com.cookie14.foodcomment.api.TestList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(val view: MainView){
    private val disposable = CompositeDisposable()
    var testData : List<TestList>? = null

    fun testRetrofit(){
            addDisposable(
                DataModel().getListRepo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Resource<List<TestList>>>(){
                        override fun onSuccess(t: Resource<List<TestList>>) {
                            println(t.code)
                            println(t.data.toString())
                            testData = t.data
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