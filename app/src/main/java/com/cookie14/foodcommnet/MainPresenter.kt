package com.cookie14.foodcommnet

import com.cookie14.foodcommnet.api.DataModel
import com.cookie14.foodcommnet.api.Resource
import com.cookie14.foodcommnet.api.TestList
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
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