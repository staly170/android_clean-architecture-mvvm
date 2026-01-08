package com.nestpay.pg.data.repository.remote.datasource.github

import com.nestpay.pg.data.model.remote.GithubEntity

interface GithubRemoteDataSource {

    suspend fun getGithub(owner: String): List<GithubEntity>?
}