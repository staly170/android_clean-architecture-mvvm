package com.nestpay.pg.domain.repository.remote

import com.nestpay.pg.domain.model.remote.Github

interface GithubRepository{

    suspend fun getGithub(owner: String): List<Github>?
}