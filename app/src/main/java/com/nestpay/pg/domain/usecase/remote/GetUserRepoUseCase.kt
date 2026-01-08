package com.nestpay.pg.domain.usecase.remote

import com.nestpay.pg.domain.model.remote.Github
import com.nestpay.pg.domain.repository.remote.GithubRepository
import com.nestpay.pg.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetUserRepoUseCase @Inject constructor(
    private val githubRepository: GithubRepository,
) : BaseUseCase() {

    //suspend fun execute(owner: String) = githubRepository.getGithub(owner)
    suspend fun getUserRepo(
        owner: String,
        scope: CoroutineScope,
        onSuccess: ((List<Github>?) -> Unit),
        onError: ((String?) -> Unit),
    ) = requestApi(
        scope = scope,
        data = {
            githubRepository.getGithub(owner)
        },
        onSuccess = {
            onSuccess(it)
        },
        onError = {
            onError(it)
        }
    )
}