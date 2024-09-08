package com.aarondeveloper.ticketing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.aarondeveloper.ticketing.data.local.database.PrioridadDb
import com.aarondeveloper.ticketing.presentation.navigation.NavigationNavHost
import com.aarondeveloper.ticketing.ui.theme.TicketingTheme

class MainActivity : ComponentActivity() {

    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).build()

        enableEdgeToEdge()

        val prioridadDao = prioridadDb.prioridadDao()
        setContent {
            TicketingTheme {
                NavigationNavHost(
                    PrioridadesLista = prioridadDao.getAll(),
                    prioridadDao = prioridadDao
                )
            }
        }
    }
}
