package com.task.bosta.ui.fragments.profile.useCases

import com.task.bosta.model.response.profile.users.UsersResponse
import com.task.bosta.repo.DataRepository
import com.task.bosta.repo.network.coroutinesUseCase.BaseUseCase
import javax.inject.Inject


class UsersUseCase @Inject constructor(dataRepository: DataRepository) : BaseUseCase<List<UsersResponse>>(dataRepository) {

    override suspend fun executeOnBackground(map: MutableMap<String, Any>, headerMap: Map<String , Any>?): List<UsersResponse> {
        return dataRepository.userProfileService()
    }

}