package com.navi.githubpulls.network

import com.navi.githubpulls.model.PullRequest
import retrofit2.Response

interface GithubNetworkSource {

    suspend fun fetchClosedPulls(): Response<PullRequest>
}