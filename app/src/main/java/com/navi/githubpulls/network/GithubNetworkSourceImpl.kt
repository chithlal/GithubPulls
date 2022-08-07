package com.navi.githubpulls.network

import com.navi.githubpulls.model.PullRequest
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

class GithubNetworkSourceImpl @Inject constructor(val githubApi: GithubApiService): GithubNetworkSource {
    override suspend fun fetchClosedPulls(): Response<PullRequest> = githubApi.fetchClosedPulls()
}