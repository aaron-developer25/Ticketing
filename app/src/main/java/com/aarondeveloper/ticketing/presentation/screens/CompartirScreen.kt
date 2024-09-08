package com.aarondeveloper.ticketing.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CompartirScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Compartir Screen")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CompartirScreenPreview() {
    CompartirScreen()
}