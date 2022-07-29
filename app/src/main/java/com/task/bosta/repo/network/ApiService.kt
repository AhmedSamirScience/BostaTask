package com.task.bosta.repo.network

import com.task.bosta.model.response.album.PhotosResponse
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.model.response.profile.users.UsersResponse
import retrofit2.http.*
/*
    Include all API Services
 */
interface ApiService {

    @GET("users")
    suspend fun userProfileService() : List<UsersResponse>
    @GET("albums")
    suspend fun albumProfileService(@Query ("userId") id : String) :  List<AlbumsResponse>


    @GET("photos")
    suspend fun photosAlbumService(@Query ("albumId") id : String) :  List<PhotosResponse>

}