package com.aarondeveloper.ticketing.presentation.navigation


import NavigationDrawer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aarondeveloper.ticketing.presentation.screens.AjustesScreen
import com.aarondeveloper.ticketing.presentation.screens.CompartirScreen
import com.aarondeveloper.ticketing.presentation.screens.HomeScreen
import com.aarondeveloper.ticketing.presentation.screens.InformacionScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.CreatePrioridadesScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.DeletePrioridadesScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.EditPrioridadesScreen
import com.aarondeveloper.ticketing.presentation.screens.perioridades.IndexPrioridadesScreen
import com.aarondeveloper.ticketing.presentation.screens.tickets.CreateTicketScreen
import com.aarondeveloper.ticketing.presentation.screens.tickets.DeleteTicketsScreen
import com.aarondeveloper.ticketing.presentation.screens.tickets.EditTicketScreen
import com.aarondeveloper.ticketing.presentation.screens.tickets.IndexTicketsScreen
import kotlinx.serialization.Serializable


@Composable
fun NavigationNavHost(
    navHostController: NavHostController
) {
    val isDrawerVisible = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navHostController, startDestination = Screen.Home) {

            composable<Screen.Home> {
                HomeScreen(
                    onDrawerToggle = { isDrawerVisible.value = !isDrawerVisible.value }
                )
            }

            composable<Screen.ControlPanelPrioridades> {
                IndexPrioridadesScreen(
                    onDrawerToggle = {
                        isDrawerVisible.value = !isDrawerVisible.value
                    },
                    goToPrioridad = {
                        navHostController.navigate(Screen.ControlPanelPrioridades)
                    },
                    createPrioridad = {
                        navHostController.navigate(Screen.CrearPrioridades)
                    },
                    editPrioridad = {
                        navHostController.navigate(Screen.EditarPrioridades(it))
                    },
                    deletePrioridad = {
                        navHostController.navigate(Screen.EliminarPrioridades(it))
                    }
                )
            }

            composable<Screen.CrearPrioridades> {
                CreatePrioridadesScreen(
                    onDrawerToggle = {
                        isDrawerVisible.value = !isDrawerVisible.value
                    },
                    goToPrioridad = {
                        navHostController.navigate(Screen.ControlPanelPrioridades)
                    }
                )
            }

            composable<Screen.EditarPrioridades> { backStackEntry ->
                val prioridadId = backStackEntry.arguments?.getInt("prioridadId")
                if (prioridadId != null) {

                    EditPrioridadesScreen(
                        prioridadId = prioridadId,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToPrioridad = {
                            navHostController.navigate(Screen.ControlPanelPrioridades)
                        }
                    )
                }
            }

            composable<Screen.EliminarPrioridades> { backStackEntry ->
                val prioridadId = backStackEntry.arguments?.getInt("prioridadId")
                if (prioridadId != null) {

                    DeletePrioridadesScreen(
                        prioridadId = prioridadId,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToPrioridad = {
                            navHostController.navigate(Screen.ControlPanelPrioridades)
                        }
                    )
                }
            }




            composable<Screen.ControlPanelTickets> {
                IndexTicketsScreen(
                    onDrawerToggle = {
                        isDrawerVisible.value = !isDrawerVisible.value
                    },
                    goToTicket = {
                        navHostController.navigate(Screen.ControlPanelTickets)
                    },
                    createTicket = {
                        navHostController.navigate(Screen.CrearTickets)
                    },
                    editTicket = {
                        navHostController.navigate(Screen.EditarTickets(it))
                    },
                    deleteTicket = {
                        navHostController.navigate(Screen.EliminarTickets(it))
                    }
                )
            }


            composable<Screen.CrearTickets> {
                CreateTicketScreen(
                    onDrawerToggle = {
                        isDrawerVisible.value = !isDrawerVisible.value
                    },
                    goToTicket = {
                        navHostController.navigate(Screen.ControlPanelTickets)
                    }
                )
            }

            composable<Screen.EditarTickets> { backStackEntry ->
                val ticketId = backStackEntry.arguments?.getInt("ticketId")
                if (ticketId != null) {

                    EditTicketScreen(
                        ticketId = ticketId,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToTicket = {
                            navHostController.navigate(Screen.ControlPanelTickets)
                        }
                    )
                }
            }

            composable<Screen.EliminarTickets> { backStackEntry ->
                val ticketId = backStackEntry.arguments?.getInt("ticketId")
                if (ticketId != null) {

                    DeleteTicketsScreen(
                        ticketId = ticketId,
                        onDrawerToggle = {
                            isDrawerVisible.value = !isDrawerVisible.value
                        },
                        goToTicket = {
                            navHostController.navigate(Screen.ControlPanelTickets)
                        }
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
                    "Home" -> navHostController.navigate(Screen.Home)
                    "Prioridades" -> navHostController.navigate(Screen.ControlPanelPrioridades)
                    "Tickets" -> navHostController.navigate(Screen.ControlPanelTickets)
                    "Compartir" -> navHostController.navigate(Screen.Compartir)
                    "Ajustes" -> navHostController.navigate(Screen.Ajustes)
                    "Información" -> navHostController.navigate(Screen.Informacion)
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
    data class EditarPrioridades(val prioridadId: Int?) : Screen()
    @Serializable
    data class EliminarPrioridades(val prioridadId: Int) : Screen()


    //Tickets
    @Serializable
    object ControlPanelTickets : Screen()
    @Serializable
    object CrearTickets : Screen()
    @Serializable
    data class EditarTickets(val ticketId: Int?) : Screen()
    @Serializable
    data class EliminarTickets(val ticketId: Int) : Screen()


    @Serializable
    object Compartir : Screen()
    @Serializable
    object Ajustes : Screen()
    @Serializable
    object Informacion : Screen()
}
