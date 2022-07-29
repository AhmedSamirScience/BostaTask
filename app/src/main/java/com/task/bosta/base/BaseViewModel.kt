package com.task.bosta.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.bosta.BostaTaskApp
import com.task.bosta.R

abstract class BaseViewModel : ViewModel() {
    var params = mutableMapOf<String,Any>()

    var networkStatus = false
    var errorDialog: MutableLiveData<String> = MutableLiveData()
    var successDialog: MutableLiveData<String> = MutableLiveData()
    fun showServerMessageException(){
        if (networkStatus){
            errorDialog.value = BostaTaskApp.instance.getString(R.string.server_error)
        }else{
            errorDialog.value = BostaTaskApp.instance.getString(R.string.no_network)
        }
    }
    fun showErrorResponseMessage(){
        errorDialog.value = BostaTaskApp.instance.getString(R.string.no_data_found)
    }

    open fun stop(){}
    open fun start(){}
}