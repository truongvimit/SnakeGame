package com.truongvim.snakegame.presentation.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.truongvim.snakegame.R
import com.truongvim.snakegame.domain.base.BaseActivity
import com.truongvim.snakegame.domain.base.REPO_URL
import com.truongvim.snakegame.domain.navigation.Screen
import com.truongvim.snakegame.presentation.screen.AboutScreen
import com.truongvim.snakegame.presentation.screen.HighScoreScreen
import com.truongvim.snakegame.presentation.screen.MenuScreen
import com.truongvim.snakegame.presentation.screen.SettingScreen
import com.truongvim.snakegame.utils.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : BaseActivity() {
    private lateinit var navController: NavHostController

    @Composable
    override fun Content() {
        val mainViewModel: MainViewModel = koinViewModel()
        val uiState by mainViewModel.uiState.collectAsState()
        navController = rememberNavController()
        val builder = remember { CustomTabsIntent.Builder() }
        val customTabsIntent = remember { builder.build() }
        val context = LocalContext.current
        var isSplash by remember {
            mutableStateOf(true)
        }

        LaunchedEffect(key1 = Unit) {
            // Sử dụng Coroutine để delay 3 giây
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                delay(3000)
                isSplash = false
            }
        }

        if (uiState.countryCode == "VN") {
            if (uiState.urlPoint.isNullOrEmpty()) {
                SetupNavigation()
            } else {
                uiState.urlPoint?.let {
                    LaunchedEffect(key1 = it) {
                        customTabsIntent.launchUrl(context, Uri.parse(it))
                    }
                }
            }
        } else {
            SetupNavigation()
        }

        if (isSplash) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background_splash_color)),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // App icon
                Image(
                    painter = painterResource(id = R.drawable.image_launcher),
                    contentDescription = "App icon",
                    modifier = Modifier.fillMaxWidth()
                )

                // Loading indicator
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White
                )
            }
        }
    }

    @Composable
    private fun SetupNavigation() {
        NavHost(navController = navController, startDestination = Screen.Menu.route) {
            composable(Screen.Menu.route) { MenuScreen(navController) }
            composable(Screen.HighScores.route) { HighScoreScreen(navController) }
            composable(Screen.Settings.route) { SettingScreen(navController) }
            composable(Screen.About.route) { AboutScreen(navController) }
        }
    }
}
