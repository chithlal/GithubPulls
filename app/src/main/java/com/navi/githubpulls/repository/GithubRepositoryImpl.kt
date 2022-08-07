package com.navi.githubpulls.repository

import android.util.Log
import com.navi.githubpulls.model.PullRequest
import com.navi.githubpulls.network.GithubNetworkSource
import dagger.hilt.android.AndroidEntryPoint


class GithubRepositoryImpl(val githubNetworkSource: GithubNetworkSource) : GithubRepository {
    override suspend fun getClosedPulls(): PullRequest? {
        val resp = githubNetworkSource.fetchClosedPulls()
        return if (resp.isSuccessful) {
            resp.body() ?: run {
                Log.d("GITHUB Repo", "getClosedPulls: null ")
                PullRequest()
            }
        } else {
            Log.d("GITHUB Repo", "getClosedPulls: Something went wrong ")
            null
        }
    }
}