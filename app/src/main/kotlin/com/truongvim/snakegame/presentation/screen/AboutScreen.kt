package com.truongvim.snakegame.presentation.screen

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.truongvim.snakegame.BuildConfig
import com.truongvim.snakegame.R
import com.truongvim.snakegame.domain.base.REPO_URL
import com.truongvim.snakegame.presentation.component.*
import com.truongvim.snakegame.presentation.theme.border2dp
import com.truongvim.snakegame.presentation.theme.padding16dp
import com.truongvim.snakegame.presentation.theme.padding8dp
import com.truongvim.snakegame.presentation.theme.width248dp

@Composable
fun AboutScreen(navController: NavHostController) {
    val context = LocalContext.current
    val builder = remember { CustomTabsIntent.Builder() }
    val customTabsIntent = remember { builder.build() }
    AppBar(
        title = stringResource(R.string.title_about),
        onBackClicked = { navController.popBackStack() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = padding16dp,
                    start = padding16dp,
                    end = padding16dp
                )
                .border(width = border2dp, color = MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DisplayLarge(text = stringResource(id = R.string.app_name))
            TitleLarge(
                modifier = Modifier.padding(padding8dp),
                text = BuildConfig.VERSION_NAME
            )
            BodyLarge(
                modifier = Modifier.padding(padding16dp),
                text = stringResource(R.string.about_game),
                textAlign = TextAlign.Justify
            )
            AppButton(
                modifier = Modifier.width(width248dp),
                text = stringResource(R.string.source_code)
            ) { customTabsIntent.launchUrl(context, Uri.parse(REPO_URL)) }
        }
    }
}