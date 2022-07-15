package com.cookie14.foodcommnet.api

class Resource<T> {
    var code: String? = null
    //var status: String? = null
    //var message: String? = null
    var data: T? = null
        private set

    fun setData(data: T) {
        this.data = data
    }
}