package com.task.bosta.ui.fragments.profile.clickEvents

import android.view.View
import com.task.bosta.model.response.profile.albums.AlbumsResponse

interface AlbumsClickListener {

    fun onAlbumClick(view: View , item: AlbumsResponse)

}