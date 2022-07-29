package com.task.bosta.di

import com.task.bosta.repo.DataRepository
import com.task.bosta.repo.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {
    /*
    Module which provides my DataRepository
    */
    @Provides
    @ViewModelScoped
    fun providesDataRepository(apiService: ApiService): DataRepository {
        return DataRepository(apiService)
    }
}