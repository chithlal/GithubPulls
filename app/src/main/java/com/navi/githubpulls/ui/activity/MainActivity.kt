package com.navi.githubpulls.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.navi.githubpulls.R
import com.navi.githubpulls.ui.components.ErrorUI
import com.navi.githubpulls.ui.components.LoadingUI
import com.navi.githubpulls.ui.components.MainUI
import com.navi.githubpulls.ui.theme.GithubPullsTheme
import com.navi.githubpulls.utils.State
import com.navi.githubpulls.utils.State.ERROR
import com.navi.githubpulls.utils.State.LOADING
import com.navi.githubpulls.utils.State.SUCCESS
import com.navi.githubpulls.utils.isNetworkAvailable
import com.navi.githubpulls.viewmodel.GithubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: GithubViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            GithubPullsTheme {
                //change status bar color according to theme
                this.window.statusBarColor = if (MaterialTheme.colors.isLight) ContextCompat.getColor(
                    this,
                    R.color.white
                ) else ContextCompat.getColor(this, R.color.dark_gray)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,

                    ) {

                    if (isNetworkAvailable(context = LocalContext.current)) {
                        LaunchedEffect(key1 = true ){
                            viewModel.refreshAndGetClosedPulls()
                        }

                    }
                    val prState = viewModel.closedPullsLiveData.observeAsState()
                    when (prState.value?.state) {
                        SUCCESS -> MainUI(pullRequest = prState.value!!.data!!, modifier = Modifier)
                        LOADING -> LoadingUI(modifier = Modifier)
                        else->{
                            if (!isNetworkAvailable(LocalContext.current)) ErrorUI(text = "No Internet!") else ErrorUI(
                                text = "Oops! something went wrong",
                                Modifier
                            )
                        }
                    }
                    Log.d("Home", "onCreate: ${prState.value?.data}")
                    //Log.d("Home", "onCreate: ${viewModel.value.closedPullsLiveData.value}")
                    BackHandler(enabled = true) {
                        finish()
                    }
                }
            }
        }
    }
}



