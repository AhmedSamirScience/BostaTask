package com.task.bosta.ui.fragments.profile

import com.task.bosta.base.BaseViewModel
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.model.response.profile.users.UsersResponse
import com.task.bosta.repo.network.MyRequestMap
import com.task.bosta.ui.fragments.profile.useCases.AlbumsUseCase
import com.task.bosta.ui.fragments.profile.useCases.UsersUseCase
import com.task.bosta.utils.LiveDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UsersUseCase,
    private val albumUseCase: AlbumsUseCase
    ): BaseViewModel() {

    override fun stop() {
        super.stop()
        userUseCase.unsubscribe()
        albumUseCase.unsubscribe()
    }

    override fun start() {
        super.start()
        loadDataUser()
    }

    //User Data
    private val _UsersLiveDate: MutableStateFlow<LiveDataResource<List<UsersResponse>>> = MutableStateFlow(LiveDataResource.EmptyState())
    val usersLiveDate: StateFlow<LiveDataResource<List<UsersResponse>>> get() = _UsersLiveDate
    fun loadDataUser(){
        Timber.e("Inside loadHomePageData")
        _UsersLiveDate.value = (LiveDataResource.Loading())
        userUseCase.execute({
            onComplete {
                if(!it.isNullOrEmpty())
                    _UsersLiveDate.value = LiveDataResource.Success(it)
                else
                {
                    _UsersLiveDate.value = LiveDataResource.ErrorResponse(it)
                    showErrorResponseMessage()
                }
            }
            onError {
               showServerMessageException()
                if (networkStatus){
                    _UsersLiveDate.value = LiveDataResource.Exception()
                }else{
                    _UsersLiveDate.value = LiveDataResource.NoNetwork()
                }
            }
            onCancel {
                _UsersLiveDate.value = (LiveDataResource.EmptyState())
            }
        }, params )
    }

    //User Album
    private val _AlbumsLiveDate: MutableStateFlow<LiveDataResource< List<AlbumsResponse>>> = MutableStateFlow(LiveDataResource.EmptyState())
    val albumsLiveDate: StateFlow<LiveDataResource< List<AlbumsResponse>>> get() = _AlbumsLiveDate
    fun loadAlbumUser(userId : String ){
        params[MyRequestMap.GET_ALBUM_BY_USER_ID] =  userId

        _AlbumsLiveDate.value = (LiveDataResource.Loading())
        albumUseCase.execute({
            onComplete {
                if(!it.isNullOrEmpty())
                    _AlbumsLiveDate.value = LiveDataResource.Success(it)
                else
                {
                    _AlbumsLiveDate.value = LiveDataResource.ErrorResponse(it)
                    showErrorResponseMessage()
                }
            }
            onError {
                showServerMessageException()
                if (networkStatus){
                    _AlbumsLiveDate.value = LiveDataResource.Exception()
                }else{
                    _AlbumsLiveDate.value = LiveDataResource.NoNetwork()
                }
            }
            onCancel {
                _AlbumsLiveDate.value = (LiveDataResource.EmptyState())
            }
        }, params )
    }

}
