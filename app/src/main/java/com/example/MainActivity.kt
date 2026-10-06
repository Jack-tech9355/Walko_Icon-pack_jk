package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.WalkoTab
import com.example.ui.WalkoViewModel
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.ApplyScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MockupScreen
import com.example.ui.screens.RequestScreen
import com.example.ui.screens.StudioScreen
import com.example.ui.theme.AmoledBlack
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.DeepObsidian
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.MidnightSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WalkoIconsTheme

class MainActivity : ComponentActivity() {

    private val viewModel: WalkoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WalkoIconsTheme {
                WalkoApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun WalkoApp(viewModel: WalkoViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val notification by viewModel.notification.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(notification) {
        notification?.let {
            snackbarHostState.showSnackbar(it.message)
            viewModel.dismissNotification()
        }
    }

    // BackHandler: Return to Home tab if on another screen
    BackHandler(enabled = currentTab != WalkoTab.HOME) {
        viewModel.selectTab(WalkoTab.HOME)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AmoledBlack),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            WalkoBottomNavigation(
                currentTab = currentTab,
                onSelectTab = { viewModel.selectTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { tab ->
                when (tab) {
                    WalkoTab.HOME -> HomeScreen(viewModel = viewModel)
                    WalkoTab.STUDIO -> StudioScreen(viewModel = viewModel)
                    WalkoTab.APPLY -> ApplyScreen(viewModel = viewModel)
                    WalkoTab.REQUEST -> RequestScreen(viewModel = viewModel)
                    WalkoTab.MOCKUP -> MockupScreen(viewModel = viewModel)
                    WalkoTab.ABOUT -> AboutScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun WalkoBottomNavigation(
    currentTab: WalkoTab,
    onSelectTab: (WalkoTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DeepObsidian)
            .border(1.dp, BorderSubtle)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.testTag("walko_bottom_nav")
        ) {
            // 1. Home
            NavigationBarItem(
                selected = currentTab == WalkoTab.HOME,
                onClick = { onSelectTab(WalkoTab.HOME) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == WalkoTab.HOME) Icons.Filled.GridView else Icons.Outlined.GridView,
                        contentDescription = "Icons",
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text("Icons", fontSize = 10.sp, fontWeight = if (currentTab == WalkoTab.HOME) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )

            // 2. Studio
            NavigationBarItem(
                selected = currentTab == WalkoTab.STUDIO,
                onClick = { onSelectTab(WalkoTab.STUDIO) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == WalkoTab.STUDIO) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
                        contentDescription = "Studio",
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text("Studio", fontSize = 10.sp, fontWeight = if (currentTab == WalkoTab.STUDIO) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )

            // 3. Apply
            NavigationBarItem(
                selected = currentTab == WalkoTab.APPLY,
                onClick = { onSelectTab(WalkoTab.APPLY) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == WalkoTab.APPLY) Icons.Filled.RocketLaunch else Icons.Outlined.RocketLaunch,
                        contentDescription = "Apply",
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text("Apply", fontSize = 10.sp, fontWeight = if (currentTab == WalkoTab.APPLY) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )

            // 4. Request
            NavigationBarItem(
                selected = currentTab == WalkoTab.REQUEST,
                onClick = { onSelectTab(WalkoTab.REQUEST) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == WalkoTab.REQUEST) Icons.Filled.MarkEmailRead else Icons.Outlined.MarkEmailRead,
                        contentDescription = "Request",
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text("Request", fontSize = 10.sp, fontWeight = if (currentTab == WalkoTab.REQUEST) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )

            // 5. Mockup
            NavigationBarItem(
                selected = currentTab == WalkoTab.MOCKUP,
                onClick = { onSelectTab(WalkoTab.MOCKUP) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == WalkoTab.MOCKUP) Icons.Filled.PhoneAndroid else Icons.Outlined.PhoneAndroid,
                        contentDescription = "Mockup",
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text("Mockup", fontSize = 10.sp, fontWeight = if (currentTab == WalkoTab.MOCKUP) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )

            // 6. About
            NavigationBarItem(
                selected = currentTab == WalkoTab.ABOUT,
                onClick = { onSelectTab(WalkoTab.ABOUT) },
                icon = {
                    Icon(
                        imageVector = if (currentTab == WalkoTab.ABOUT) Icons.Filled.Info else Icons.Outlined.Info,
                        contentDescription = "About",
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text("About", fontSize = 10.sp, fontWeight = if (currentTab == WalkoTab.ABOUT) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = NeonCyan,
                    indicatorColor = NeonCyan,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}
