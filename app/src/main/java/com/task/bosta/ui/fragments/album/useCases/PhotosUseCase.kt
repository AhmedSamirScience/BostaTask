package com.task.bosta.ui.fragments.album.useCases

import com.task.bosta.model.response.album.PhotosResponse
import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.repo.DataRepository
import com.task.bosta.repo.network.coroutinesUseCase.BaseUseCase
import javax.inject.Inject


class PhotosUseCase @Inject constructor(dataRepository: DataRepository) : BaseUseCase< List<PhotosResponse>>(dataRepository) {

    override suspend fun executeOnBackground(map: MutableMap<String, Any>, headerMap: Map<String , Any>?):  List<PhotosResponse> {
        return dataRepository.photosAlbumService(map)
    }

}

