package com.polytechtest.ui.dialogs.buy

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.fragment.navArgs
import com.polytechtest.R
import com.polytechtest.arch.BaseComposeDialogFragment
import com.polytechtest.ui.composable.BlackButton

class DialogBuy : BaseComposeDialogFragment(
    heightPercent = 0.8f,
    ratio = 0.5f,
    styleRes = R.style.Theme_PolyTechTest,
    backgroundRes = R.drawable.shape_dialog_bg
) {

    private val args: DialogBuyArgs by navArgs()
    private lateinit var webView: WebView

    @Composable
    override fun DialogFragmentScreen() {

        Column {
            AndroidView(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.925f), factory = {
                WebView(it).apply {
                    webView = this
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(args.link)
                }
            }, update = {
                it.loadUrl(args.link)
            })

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.LightGray)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.1f)
                )
                BlackButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(bottom = 4.dp, top = 4.dp),
                    stringResource(R.string.back)
                ) { if (webView.canGoBack()) webView.goBack() }
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.1f)
                )
                BlackButton(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(bottom = 4.dp, top = 4.dp), stringResource(R.string.close)
                ) { requireDialog().dismiss() }
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.1f)
                )
            }
        }
    }
}
