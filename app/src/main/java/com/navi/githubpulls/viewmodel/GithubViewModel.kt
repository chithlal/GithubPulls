package com.navi.githubpulls.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navi.githubpulls.model.PullRequest
import com.navi.githubpulls.repository.GithubRepository
import com.navi.githubpulls.utils.ResponseState
import com.navi.githubpulls.utils.State.ERROR
import com.navi.githubpulls.utils.State.LOADING
import com.navi.githubpulls.utils.State.SUCCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(val githubRepository: GithubRepository) : ViewModel() {
    private val _closedPullsLiveData: MutableLiveData<ResponseState<PullRequest>> = MutableLiveData()
    val closedPullsLiveData: LiveData<ResponseState<PullRequest>> = _closedPullsLiveData

    fun refreshAndGetClosedPulls() {
        _closedPullsLiveData.value = ResponseState(LOADING, "Loading", PullRequest())
        viewModelScope.launch {
            val pullRequests = githubRepository.getClosedPulls()
            if (pullRequests.isNullOrEmpty().not()) {
                _closedPullsLiveData.value = ResponseState(SUCCESS, "Success", pullRequests)
            } else {
                _closedPullsLiveData.value = ResponseState(ERROR, "Error", pullRequests)
            }
        }
    }

}