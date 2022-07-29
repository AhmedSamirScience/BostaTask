package com.task.bosta.model.response.profile.albums

import com.google.gson.annotations.SerializedName

data class AlbumsResponse(
    @SerializedName("userId")
    val userID: Long,
    val id: Long,
    val title: String
)
