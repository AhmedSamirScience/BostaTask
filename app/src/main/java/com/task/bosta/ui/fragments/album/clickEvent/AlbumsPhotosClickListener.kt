package com.task.bosta.ui.fragments.album.clickEvent

import android.view.View
import com.task.bosta.model.response.album.PhotosResponse

interface AlbumsPhotosClickListener {

    fun onPhotoClick(view: View , item: PhotosResponse)

}