package com.task.bosta.ui.fragments.profile.useCases

import com.task.bosta.model.response.profile.albums.AlbumsResponse
import com.task.bosta.repo.DataRepository
import com.task.bosta.repo.network.coroutinesUseCase.BaseUseCase
import javax.inject.Inject


class AlbumsUseCase @Inject constructor(dataRepository: DataRepository) : BaseUseCase< List<AlbumsResponse>>(dataRepository) {

    override suspend fun executeOnBackground(map: MutableMap<String, Any>, headerMap: Map<String , Any>?):  List<AlbumsResponse> {
        return dataRepository.albumProfileService(map)
    }

}