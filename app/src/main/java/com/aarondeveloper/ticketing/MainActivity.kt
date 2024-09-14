package com.aarondeveloper.ticketing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.aarondeveloper.ticketing.presentation.navigation.NavigationNavHost
import com.aarondeveloper.ticketing.ui.theme.TicketingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicketingTheme {
                val navHost = rememberNavController()
                NavigationNavHost(navHost)
            }
        }
    }
}
