package com.navi.githubpulls.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.navi.githubpulls.R
import com.navi.githubpulls.ui.components.MainUI
import com.navi.githubpulls.ui.theme.GithubPullsTheme
import com.navi.githubpulls.utils.State.ERROR
import com.navi.githubpulls.utils.State.LOADING
import com.navi.githubpulls.utils.State.SUCCESS
import com.navi.githubpulls.viewmodel.GithubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: GithubViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            GithubPullsTheme {
                // A surface container using the 'background' color from the theme
                this.window.statusBarColor = if (MaterialTheme.colors.isLight) ContextCompat.getColor(
                    this,
                    R.color.white
                ) else ContextCompat.getColor(this, R.color.dark_gray)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,

                    ) {

                    viewModel.closedPullsLiveData.observe(this) {
                        Log.d("Home", "onCreate: ${it.data}")

                    }
                    val prState = viewModel.closedPullsLiveData.observeAsState()
                    when(prState.value?.state){
                        SUCCESS-> MainUI(pullRequest = prState.value!!.data!!, modifier = Modifier)
                        LOADING -> {}//LoadingUI()
                        ERROR -> {}//ErrorUI()
                    }

                    //Log.d("Home", "onCreate: ${viewModel.value.closedPullsLiveData.value}")
                }
            }
        }
    }
}



