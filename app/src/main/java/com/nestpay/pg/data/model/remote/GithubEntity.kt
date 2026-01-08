package com.nestpay.pg.data.model.remote

import com.squareup.moshi.Json

data class GithubEntity(

    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "created_at")
    val created_at: String,
    @Json(name = "html_url")
    val url: String,
)
