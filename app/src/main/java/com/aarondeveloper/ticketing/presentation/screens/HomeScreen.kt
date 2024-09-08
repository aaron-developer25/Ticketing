package com.aarondeveloper.ticketing.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(onDrawerToggle: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_principal),
            contentDescription = "Background Principal",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Button(
            onClick = onDrawerToggle,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp)
                .border(2.dp, Lavender, shape = RoundedCornerShape(10.dp))
                .background(Color.Transparent),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ico_menu),
                    contentDescription = "Menu Icon",
                    tint = Lavender,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Menu",
                    color = Lavender,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        BienvenidoAnimado()
    }
}

@Composable
fun BienvenidoAnimado() {
    var displayedText by remember { mutableStateOf("") }
    val fullText = "Bienvenid@s!"

    LaunchedEffect(Unit) {
        while (true) {
            for (i in fullText.indices) {
                displayedText = fullText.substring(0, i + 1)
                delay(200)
            }
            delay(200)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = displayedText,
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            modifier = Modifier
                .padding(bottom = 150.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onDrawerToggle = { /* */ })
}
