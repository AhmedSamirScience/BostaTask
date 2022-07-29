package com.task.bosta.di

import android.app.Application
import android.content.Context
import com.task.bosta.utils.ConnectionLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        ConnectionLiveData(context as Application)
}