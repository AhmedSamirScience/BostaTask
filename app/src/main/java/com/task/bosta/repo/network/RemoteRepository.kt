package com.task.bosta.repo.network

import com.task.bosta.model.response.album.PhotosResponse
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.model.response.profile.users.UsersResponse

interface RemoteRepository {

   suspend fun userProfileService() : List<UsersResponse>
   suspend fun albumProfileService( mapBody: MutableMap<String, Any>) :  List<AlbumsResponse>
   suspend fun photosAlbumService(mapBody: MutableMap<String, Any>) :  List<PhotosResponse>

}