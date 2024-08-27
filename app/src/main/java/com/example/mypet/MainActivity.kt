package com.example.mypet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypet.PetlistScreen.AichatScreen
import com.example.mypet.PetlistScreen.CatlistScreen
import com.example.mypet.PetlistScreen.DoglistScreen
import com.example.mypet.mainScreen.EntryScreen
import com.example.mypet.mainScreen.MainScreen
import com.example.mypet.mainScreen.MenuScreen
import com.example.mypet.mainScreen.SetScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkModeViewModel: DarkModeViewModel = viewModel()
            MyApp(darkModeViewModel)
        }
    }
}

@Composable
fun MyApp(darkModeViewModel: DarkModeViewModel) {
    // 다크 모드 상태를 옵저빙하고 UI 재구성을 트리거
    val isDarkModeEnabled by darkModeViewModel.isDarkMode.collectAsState()

    // 앱 전체를 테마로 감싼다
    MyAppTheme(darkTheme = isDarkModeEnabled) {
        // 앱 콘텐츠를 렌더링
        AppContent(darkModeViewModel)
    }
}

@Composable
fun AppContent(darkModeViewModel: DarkModeViewModel) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = "entryScreen"
            ) {
                composable("mainScreen") { MainScreen(navController, darkModeViewModel) }
                composable("entryScreen") { EntryScreen(navController) }
                composable("doglistScreen") { DoglistScreen(navController, darkModeViewModel) }
                composable("catlistScreen") { CatlistScreen(navController, darkModeViewModel) }
                composable("menuScreen") { MenuScreen(navController, darkModeViewModel) }
                composable("setScreen") { SetScreen(navController, darkModeViewModel) }
                composable("aichatScreen") { AichatScreen(navController, darkModeViewModel) }
            }
        }
    }
}

class DarkModeViewModel : ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }
}

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            onPrimary = Color.Black,
            background = Color.Black,
            surface = Color.DarkGray,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            onPrimary = Color.White,
            background = Color.White,
            surface = Color.LightGray,
            onSurface = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
