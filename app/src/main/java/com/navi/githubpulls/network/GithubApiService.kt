package com.navi.githubpulls.network

import com.navi.githubpulls.model.PullRequest
import retrofit2.Response
import retrofit2.http.GET

interface GithubApiService {
    @GET("/repos/chithlal/GithubPulls/pulls?state=closed")
    suspend fun fetchClosedPulls(): Response<PullRequest>
}