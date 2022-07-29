package com.task.bosta.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.task.bosta.R
import com.task.bosta.utils.ConnectionLiveData
import com.task.bosta.utils.widgets.CustomAlertDialog
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel , VB : ViewDataBinding> : Fragment() , View.OnClickListener  {

    @Inject
    lateinit var connectivityManager: ConnectionLiveData
    var isNetwork: Boolean = false

    protected var baseViewModel: VM? = null
    lateinit var baseViewBinding : VB

    var lang = ""
    var isArabicLanguage = false

    open var dialogDismissAction: (() -> Unit)? = null

    protected abstract fun initView()
    protected abstract fun getContentView(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        baseViewBinding = DataBindingUtil.inflate(inflater , getContentView() , container , false)
        return baseViewBinding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        baseViewModel?.start()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetworkConnection()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        initView()
         subscribeLiveData()
    }
    override fun onDestroy() {
        super.onDestroy()
        baseViewModel?.stop()
    }

    private fun observeNetworkConnection() {
        connectivityManager.observe(viewLifecycleOwner, Observer{ isAvailable ->
            when (isAvailable) {
                true -> {
                    baseViewModel?.networkStatus = true
                    isNetwork = true
                }
                false ->{
                    baseViewModel?.networkStatus = false
                    isNetwork = false
                }
            }
        })
    }

    protected abstract fun initializeViewModel()
    protected open fun subscribeLiveData() {
        baseViewModel?.errorDialog?.observe(viewLifecycleOwner, Observer{
        Timber.e("Dialog State is ${viewLifecycleOwner.lifecycle.currentState}")
            if(viewLifecycleOwner.lifecycle.currentState== Lifecycle.State.RESUMED){
                showError(it, this.getString(R.string.ok), null)
            }
        })
        baseViewModel?.successDialog?.observe(viewLifecycleOwner, Observer {
         Timber.e("Dialog State is ${viewLifecycleOwner.lifecycle.currentState}")
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED){
                showSuccess(it, this.getString(R.string.ok))
            }
        })
    }


    fun showError(error: String, btnTitle: String?, myAction: (() -> Unit)?) {
        Timber.e("aaaaaaaaaaaaaaa "+ error)
        CustomAlertDialog.showMyCustomDialog(
            requireActivity() as AppCompatActivity,
            CustomAlertDialog.Type.ERROR,
            btnTitle!!,
            error,
            myAction
        )
    }
    fun showSuccess(msg: String, btnTitle: String?) {
        if (btnTitle.isNullOrEmpty()){
            CustomAlertDialog.showMyCustomDialog(
                requireActivity() as AppCompatActivity, CustomAlertDialog.Type.SUCCESS, this.getString(
                    R.string.ok
                ), msg, dialogDismissAction
            )
        }else{
            CustomAlertDialog.showMyCustomDialog(
                requireActivity() as AppCompatActivity,
                CustomAlertDialog.Type.SUCCESS,
                btnTitle,
                msg,
                dialogDismissAction
            )
        }
    }



}



