package com.task.bosta.model.response.album

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("albumId")
    val albumID: Long,
    val id: Long,
    val title: String,
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailURL: String
)
