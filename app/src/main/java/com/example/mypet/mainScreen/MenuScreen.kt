package com.example.mypet.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mypet.DarkModeViewModel
import com.example.mypet.MyApp
import com.example.mypet.R

@Composable
fun MenuScreen(navController: NavController,darkModeViewModel: DarkModeViewModel) {
    MyApp(darkModeViewModel = darkModeViewModel)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF6F61),
                            Color(0xFFFF8A65)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dog),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }

            IconButton(
                onClick = {navController.navigate("MainScreen")},
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // 아이콘 제거
                Text(text = "진단내역", color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // 아이콘 제거
                Text(text = "마이보험", color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // 아이콘 제거
                Text(text = "쿠폰함", color = Color.Gray)
            }
        }

        Divider(thickness = 1.dp, color = Color.LightGray)

        // 확장 가능한 메뉴 항목
        MenuItem(title = "펫보험", navController = navController)
        MenuItem(title = "고객센터", navController = navController)
    }
}

@Composable
fun MenuItem(title: String, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 16.sp, color = Color.Black)
            // 아이콘 제거
        }
        Divider(thickness = 1.dp, color = Color.LightGray)
    }
}