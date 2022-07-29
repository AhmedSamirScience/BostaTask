package com.task.bosta

import android.content.Context
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.*

@HiltAndroidApp
class BostaTaskApp: LocalizationApplication() {
    override fun getDefaultLanguage(context: Context): Locale {
        return Locale("en")
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        instance = this
    }

    companion object {
        lateinit var instance: BostaTaskApp
            private set
    }
}