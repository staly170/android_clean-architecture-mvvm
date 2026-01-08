package com.nestpay.pg.data.repository.remote.datasourceimpl.github

import com.nestpay.pg.data.api.ApiInterface
import com.nestpay.pg.data.model.remote.GithubEntity
import com.nestpay.pg.data.repository.remote.datasource.github.GithubRemoteDataSource
import javax.inject.Inject

class GithubRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : GithubRemoteDataSource {

    override suspend fun getGithub(owner: String): List<GithubEntity>? {
        return apiInterface.getRepos(owner).body()
    }
}