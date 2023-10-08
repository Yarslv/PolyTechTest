package com.polytechtest.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.polytechtest.R
import com.polytechtest.domain.entity.BookModel
import com.polytechtest.domain.entity.CategoryModel
import com.polytechtest.util.AppAction


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookListItem(model: BookModel, block: (AppAction) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.45f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GlideImage(
                        model = model.imageLink,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .weight(5f),
                        contentScale = ContentScale.Fit,
                        loading = placeholder(R.drawable.vector_book),
                        failure = placeholder(R.drawable.vector_book),
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.25f)
                    )
                    BlackButton(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .weight(1f), stringResource(R.string.buy)
                    )
                    { block(AppAction.Navigate.ToBuyBook(model.buyLink)) }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    BookInfoText(text = stringResource(R.string.rank_format, model.rank))
                    Text(
                        text = model.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        maxLines = 4
                    )
                    BookInfoText(text = stringResource(R.string.author_format, model.author))
                    BookInfoText(text = stringResource(R.string.publisher_format, model.publisher))
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Text(text = model.description)
            }
        }
    }
}

@Composable
fun BookInfoText(text: String) {
    Text(
        text = text, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp), fontSize = 16.sp, maxLines = 2

    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookList(
    isShowNothing: Boolean,
    pullRefreshState: PullRefreshState,
    title: String,
    block: LazyListScope.() -> Unit,
) {
    if (isShowNothing)
        EmptyScreen(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            message = stringResource(R.string.nothing_to_show)
        )
    else
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { ListTitle(title, 20.sp) }
            block()
        }
}

@Composable
fun Progress() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxHeight(0.075f)
                .aspectRatio(1f)
                .align(Alignment.Center),
            color = Color.Black,
            backgroundColor = Color.Gray,
            strokeWidth = 5.dp
        )
    }
}

@Composable
fun EmptyScreen(modifier: Modifier, message: String) {
    Box(modifier = modifier) {
        Text(modifier = Modifier.align(Alignment.Center), text = message, fontSize = 20.sp)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItem(model: CategoryModel, block: (AppAction) -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color.Gray),
        onClick = { block(AppAction.Navigate.ToBookList(model.encodedName)) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.65f)
                    .padding(vertical = 4.dp),
                text = model.name,
                fontSize = 20.sp
            )
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth(0.93f),
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.32f)
                    .padding(vertical = 4.dp),
                text = stringResource(R.string.published_format, model.publishedDate),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ListTitle(titleText: String, textSize: TextUnit) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 6.dp),
        shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color.Gray),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            text = titleText,
            fontSize = textSize,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BlackButton(modifier: Modifier, text: String, block: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) Color.DarkGray else Color.Black
    val contentColor = if (isPressed) Color.Black else Color.White

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = contentColor
        ),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(12.dp),
        onClick = block
    ) {
        Text(text = text)
    }
}