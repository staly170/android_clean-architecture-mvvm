package com.nestpay.pg.data.repository.remote.repository

import com.nestpay.pg.data.mapper.Mapper
import com.nestpay.pg.data.repository.remote.datasource.github.GithubRemoteDataSource
import com.nestpay.pg.domain.model.remote.Github
import com.nestpay.pg.domain.repository.remote.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubRemoteDataSource: GithubRemoteDataSource,
) : GithubRepository {

    override suspend fun getGithub(owner: String): List<Github>? {
        return Mapper.mapperToGithub(githubRemoteDataSource.getGithub(owner))
    }
}