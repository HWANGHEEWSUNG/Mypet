package com.example.mypet.PetlistScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mypet.DarkModeViewModel
import com.example.mypet.MyApp


data class DogItem(
    val name: String,
    val picture1: String?,
    val address: String,
    val comment: String?
)

fun decodeBase64ToBitmap(base64Str: String): Bitmap {
    val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

@Composable
fun DoglistScreen(navController: NavController,
                  darkModeViewModel: DarkModeViewModel,
                  dogItems: List<DogItem> = emptyList()
) {
    MyApp(darkModeViewModel = darkModeViewModel)

    val chatMessages = remember { mutableStateListOf<String>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
            text = "AI가 추천해주는\n강아지 맞춤 진단",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // 리스트 뷰
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)  // Remaining space for LazyColumn
        ) {
            val cardCount = if (dogItems.isEmpty()) 6 else dogItems.size

            items(cardCount) { index ->
                if (dogItems.isEmpty()) {
                    EmptyCard()  // 빈 카드
                } else {
                    DogCard(navController = navController, dogItem = dogItems[index])
                }
            }
        }
            DogChatInterface(
                navController = navController,
                chatMessages = chatMessages,
                modifier = Modifier.fillMaxWidth()
            )
        }
   }
}

@Composable
fun DogCard(
    navController: NavController? = null,
    dogItem: DogItem? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .clickable { navController?.navigate("someDestination") },  // Use safe call to avoid null pointer
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(

            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            dogItem?.picture1?.let {
                val imageBitmap = decodeBase64ToBitmap(it)
                Image(bitmap = imageBitmap.asImageBitmap(), contentDescription = "Dog Image")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dogItem?.name ?: "",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "5.0 ",
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dogItem?.address ?: "",
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = dogItem?.comment ?: "",
                color = Color(0xFF6A1B9A)
            )
        }
    }
}
@Composable
fun DogChatInterface(
    navController: NavController,
    chatMessages: MutableList<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        // LazyColumn for displaying chat messages
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 8.dp)
        ) {
            items(chatMessages) { message ->
                Text(text = message, modifier = Modifier.padding(4.dp))
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        // Header Text for the Chat Interface
        Text(
            text = "AI 챗봇 상담하기",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Text input field for new messages
        var text by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("증상 입력") },
                singleLine = true // Ensure input field stays single line
            )
            IconButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        // Add the new message to the chat list
                        chatMessages.add(text)
                        text = "" // Clear the text field
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
    }
}

@Composable
fun EmptyCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 빈 카드 내용
            Text(
                text = "Empty Card",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}
