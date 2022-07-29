package com.task.bosta.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.task.bosta.R
import com.task.bosta.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : LocalizationActivity() {

    lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private var navHost : NavHostFragment?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavHost()
    }

    private fun initNavHost(){
        navHost = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHost!!.navController
    }

}