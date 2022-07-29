package com.task.bosta.ui.fragments.album

import com.task.bosta.base.BaseViewModel
import com.task.bosta.model.response.album.PhotosResponse
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.repo.network.MyRequestMap
import com.task.bosta.ui.fragments.album.useCases.PhotosUseCase
import com.task.bosta.ui.fragments.profile.useCases.AlbumsUseCase
import com.task.bosta.utils.LiveDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val photosUseCase: PhotosUseCase,
    ): BaseViewModel() {

    override fun stop() {
        photosUseCase.unsubscribe()
    }
    override fun start() {
    }

    private val _PhotosLiveDate: MutableStateFlow<LiveDataResource<List<PhotosResponse>>> = MutableStateFlow(LiveDataResource.EmptyState())
    val photosLiveDate: StateFlow<LiveDataResource<List<PhotosResponse>>> get() = _PhotosLiveDate
    fun loadPhotos(albumId : String){
        _PhotosLiveDate.value = (LiveDataResource.Loading())
       params[MyRequestMap.GET_PHOTOS_BY_ALBUM_ID] =  albumId
      //params[MyRequestMap.GET_PHOTOS_BY_ALBUM_ID] =  "1"

        photosUseCase.execute({
            onComplete {
                if(!it.isNullOrEmpty())
                    _PhotosLiveDate.value = LiveDataResource.Success(it)
                else
                {
                    _PhotosLiveDate.value = LiveDataResource.ErrorResponse(it)
                    showErrorResponseMessage()
                }
            }
            onError {
                showServerMessageException()
                if (networkStatus){
                    _PhotosLiveDate.value = LiveDataResource.Exception()
                }else{
                    _PhotosLiveDate.value = LiveDataResource.NoNetwork()
                }
            }
            onCancel {
                Timber.e("loadHomePageData -> onCancel")
                _PhotosLiveDate.value = (LiveDataResource.EmptyState())
            }
        }, params )
    }


}