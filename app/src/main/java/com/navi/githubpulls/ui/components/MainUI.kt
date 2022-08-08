package com.navi.githubpulls.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.navi.githubpulls.R
import com.navi.githubpulls.model.PullRequest
import com.navi.githubpulls.model.PullRequestItem
import com.navi.githubpulls.ui.theme.GithubPullsTheme
import com.navi.githubpulls.ui.theme.blue
import com.navi.githubpulls.ui.theme.darkGrey
import com.navi.githubpulls.ui.theme.white
import com.navi.githubpulls.utils.getDateText

@Composable
fun MainUI(
    pullRequest: PullRequest,
    modifier: Modifier
) {
    val title = stringResource(id = R.string.title)
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { AppBar(title = title, modifier = Modifier) },
        backgroundColor = MaterialTheme.colors.primary,
        content = { UIContent(list = pullRequest, modifier = Modifier.padding(paddingValues = it)) }
    )
}

@Composable
fun AppBar(
    title: String,
    modifier: Modifier
) {
    val titleStyle = MaterialTheme.typography.body1.copy(
        fontWeight = FontWeight.Medium,
        color = if (MaterialTheme.colors.isLight) Color.Black else Color.White,
        letterSpacing = TextUnit.Unspecified
    )
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pullrequest),
                        contentDescription = "Icon",
                        colorFilter = if (MaterialTheme.colors.isLight) ColorFilter.tint(blue) else ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.appbar_icon_size))
                            .padding(1.dp)
                    )
                    Text(text = title, modifier = Modifier.padding(start = 8.dp), style = titleStyle)
                }
                TagUI(tagText = "Closed", modifier = Modifier.padding(start = 16.dp))
            }
        },
        modifier = modifier,

        )
}

@Composable
fun TagUI(
    tagText: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = tagText,
        modifier
            .background(shape = CircleShape, color = Color.Red)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        style = MaterialTheme.typography.body2.copy(color = Color.White),
        textAlign = TextAlign.Center
    )
}

@Composable
fun PullRequestUI(
    title: String,
    imageUrl: String,
    userName: String,
    createdAt: String,
    closedAt: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(), backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp
    ) {
        Column {
            ConstraintLayout(modifier = Modifier.padding(all = 16.dp)) {
                val (iconRef, titleRef, createdDateRef, closedDateRef, userNameRef, userImageRef, labelRef) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.ic_merge), contentDescription = "Icon", modifier = Modifier
                        .size(32.dp)
                        .constrainAs(iconRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                    colorFilter = if (MaterialTheme.colors.isLight) ColorFilter.tint(color = darkGrey) else ColorFilter.tint(color = white)
                )
                val textColor = if (MaterialTheme.colors.isLight) Color.Black else Color.White
                Text(
                    text = title,
                    modifier = Modifier
                        .constrainAs(titleRef) {
                            linkTo(iconRef.end, parent.end, startMargin = 16.dp, endMargin = 16.dp, bias = 0f)
                            top.linkTo(parent.top)
                            width = Dimension.preferredWrapContent

                        },
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Medium, color = textColor),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Text(
                    text = "on ${getDateText(createdAt)}",
                    modifier = Modifier
                        .padding(top = 6.dp, start = 5.dp)
                        .constrainAs(createdDateRef) {
                            start.linkTo(userNameRef.end)
                            top.linkTo(titleRef.bottom)
                        },
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.secondaryVariant,
                        fontWeight = Companion.Normal
                    )
                )
                Text(
                    text = "Created by ",
                    modifier = Modifier
                        .padding(top = 4.dp, start = 16.dp)
                        .constrainAs(labelRef) {
                            start.linkTo(iconRef.end)
                            top.linkTo(titleRef.bottom)
                        },
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.secondaryVariant,
                        fontWeight = Companion.Normal
                    )
                )

                AsyncImage(
                    model = imageUrl,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(start = 4.dp, top = 8.dp)
                        .constrainAs(userImageRef) {
                            start.linkTo(labelRef.end)
                            top.linkTo(titleRef.bottom)
                        }
                        .clip(CircleShape),
                    contentDescription = "User",
                )

                Text(
                    text = userName,
                    Modifier
                        .padding(start = 4.dp, top = 4.dp)
                        .constrainAs(userNameRef) {
                            start.linkTo(userImageRef.end)
                            top.linkTo(titleRef.bottom)
                        },
                    style = MaterialTheme.typography.body1.copy(color = blue, fontWeight = Companion.Medium)
                )
                Text(
                    text = "Closed on ${getDateText(closedAt)}",
                    Modifier
                        .padding(top = 8.dp, start = 16.dp)
                        .constrainAs(closedDateRef) {
                            start.linkTo(iconRef.end)
                            top.linkTo(labelRef.bottom)
                        },
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.secondaryVariant,
                        fontWeight = Companion.Normal
                    )
                )

            }
            Divider(
                color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.2f),
                modifier = Modifier.padding(top = 6.dp)
            )

        }
    }
}

@Composable
fun UIContent(
    list: List<PullRequestItem>,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier.padding(top = 16.dp)) {
        items(list) {
            PullRequestUI(
                title = it.title,
                imageUrl = it.user.avatarUrl,
                userName = it.user.login,
                createdAt = it.createdAt,
                closedAt = it.closedAt
            )
        }
    }
}

//Error UI

@Composable
fun ErrorUI(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, style = MaterialTheme.typography.h6.copy(color = Color.Red))
    }
}

//Loading UI
@Composable
fun LoadingUI(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.secondaryVariant, strokeWidth = 2.dp)
    }
}

// Preview
@Preview
@Composable
private fun TagUIPreview() {
    TagUI(tagText = "Closed", modifier = Modifier)
}

@Preview
@Composable
private fun MainUIPreview() {
    GithubPullsTheme {
        MainUI(pullRequest = PullRequest(), modifier = Modifier)
    }
}

@Preview
@Composable
private fun CardUIPreview() {
    PullRequestUI(
        title = "Viewmodel Integration kjdfjshdf sdkhf kjsdf",
        imageUrl = "",
        createdAt = "2 Days ago",
        closedAt = "today",
        userName = "chithlal"
    )
}

@Preview
@Composable
private fun ErrorUIPreview() {
    ErrorUI(text = "Oops! something went wrong")
}

@Preview
@Composable
private fun LoadingUIPreview() {
    LoadingUI(modifier = Modifier)
}