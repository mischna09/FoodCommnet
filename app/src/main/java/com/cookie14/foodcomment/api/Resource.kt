package com.cookie14.foodcomment.api

class Resource<T> {
    var code: String? = null
    //var status: String? = null
    //var message: String? = null
    var data: T? = null
        private set

    /*fun setData(data: MutableList<T>) {
        this.data = data
    }*/
}