package com.aarondeveloper.ticketing.presentation.navigation

import NavigationDrawer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aarondeveloper.ticketing.data.local.dao.PrioridadDao
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import com.aarondeveloper.ticketing.presentation.screens.InformacionScreen
import com.aarondeveloper.ticketing.presentation.screens.AjustesScreen
import com.aarondeveloper.ticketing.presentation.screens.CompartirScreen
import com.aarondeveloper.ticketing.presentation.screens.HomeScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.CreatePrioridadesScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.DeletePrioridadesScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.EditPrioridadesScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.IndexPrioridadesScreen


import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable


@Composable
fun NavigationNavHost(
    prioridadDao: PrioridadDao,
    PrioridadesLista: Flow<List<PrioridadEntity>>
) {
    val navController = rememberNavController()
    val isDrawerVisible = remember { mutableStateOf(false) }
    val currentScreen = remember { mutableStateOf<Screen>(Screen.Home) }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = Screen.Home) {
            composable<Screen.Home> {
                HomeScreen(onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value })
            }
            composable<Screen.ControlPanelPrioridades> {
                IndexPrioridadesScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                    PrioridadesLista = PrioridadesLista,
                    navController = navController
                )
            }
            composable<Screen.CrearPrioridades> {
                CreatePrioridadesScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                    navController = navController,
                    prioridadDao = prioridadDao
                )
            }
            composable<Screen.EditarPrioridades> { backStackEntry ->
                val prioridadId = backStackEntry.arguments?.getInt("prioridadId")
                if (prioridadId != null) {
                    EditPrioridadesScreen(
                        onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                        navController = navController,
                        prioridadDao = prioridadDao,
                        prioridadId = prioridadId
                    )
                }
            }
            composable<Screen.EliminarPrioridades> { backStackEntry ->
                val prioridadId = backStackEntry.arguments?.getInt("prioridadId")
                if (prioridadId != null) {
                    DeletePrioridadesScreen(
                        onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value },
                        navController = navController,
                        prioridadDao = prioridadDao,
                        prioridadId = prioridadId
                    )
                }
            }
            composable<Screen.Compartir> {
                CompartirScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value }
                )
            }
            composable<Screen.Ajustes> {
                AjustesScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value }
                )
            }
            composable<Screen.Informacion> {
                InformacionScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value }
                )
            }
        }

        NavigationDrawer(
            isVisible = isDrawerVisible.value,
            onItemClick = { itemTitle ->
                when (itemTitle) {
                    "Home" -> navController.navigate(Screen.Home)
                    "Prioridades" -> navController.navigate(Screen.ControlPanelPrioridades)
                    "Compartir" -> navController.navigate(Screen.Compartir)
                    "Ajustes" -> navController.navigate(Screen.Ajustes)
                    "Información" -> navController.navigate(Screen.Informacion)
                }
                isDrawerVisible.value = false
            },
            onClose = {
                isDrawerVisible.value = false
            }
        )
    }
}


sealed class Screen {
    @Serializable
    object Home : Screen()

    //Prioridades
    @Serializable
    object ControlPanelPrioridades : Screen()
    @Serializable
    object CrearPrioridades : Screen()
    @Serializable
    data class EditarPrioridades(val prioridadId: Int) : Screen()
    @Serializable
    data class EliminarPrioridades(val prioridadId: Int) : Screen()

    @Serializable
    object Compartir : Screen()
    @Serializable
    object Ajustes : Screen()
    @Serializable
    object Informacion : Screen()
}
