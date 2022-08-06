package com.navi.githubpulls.network

import com.navi.githubpulls.model.PullRequest
import retrofit2.Response
import javax.inject.Inject

class GithubNetworkSourceImplementation @Inject constructor(val githubApi: GithubApiService): GithubNetworkSource {
    override suspend fun fetchClosedPulls(): Response<PullRequest> = githubApi.fetchClosedPulls()
}