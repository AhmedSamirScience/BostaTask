package com.task.bosta.repo

import com.task.bosta.model.response.album.PhotosResponse
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.model.response.profile.users.UsersResponse
import com.task.bosta.repo.network.ApiService
import com.task.bosta.repo.network.MyRequestMap
import com.task.bosta.repo.network.RemoteRepository
import javax.inject.Inject

class DataRepository  @Inject constructor(private val apiService: ApiService) : RemoteRepository {

    override suspend fun userProfileService(): List<UsersResponse> {
        return apiService.userProfileService()
    }

    override suspend fun albumProfileService(mapBody: MutableMap<String, Any>):  List<AlbumsResponse> {
        return apiService.albumProfileService(mapBody[MyRequestMap.GET_ALBUM_BY_USER_ID] as String)
    }

    override suspend fun photosAlbumService(mapBody: MutableMap<String, Any>):  List<PhotosResponse> {
        return apiService.photosAlbumService(mapBody[MyRequestMap.GET_PHOTOS_BY_ALBUM_ID] as String)
    }


}