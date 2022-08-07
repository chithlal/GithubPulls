package com.navi.githubpulls.repository

import com.navi.githubpulls.model.PullRequest

interface GithubRepository {
    suspend fun getClosedPulls(): PullRequest?
}