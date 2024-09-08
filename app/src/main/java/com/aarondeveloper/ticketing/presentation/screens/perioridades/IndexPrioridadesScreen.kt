package com.aarondeveloper.ticketing.presentation.screens.perioridades

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import com.aarondeveloper.ticketing.presentation.navigation.Screen
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.White
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun IndexPrioridadesScreen(
    onDrawerToggle: () -> Unit,
    PrioridadesLista: Flow<List<PrioridadEntity>>,
    navController: NavController
) {
    var prioridades by remember { mutableStateOf(emptyList<PrioridadEntity>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(PrioridadesLista) {
        PrioridadesLista.collect { data ->
            prioridades = data
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_prioridades_control),
            contentDescription = "Background Principal",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Button(
            onClick = onDrawerToggle,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 50.dp, start = 5.dp)
                .border(2.dp, White, shape = RoundedCornerShape(10.dp))
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
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Menu",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 280.dp)
        ) {
            if (prioridades.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ico_busqueda),
                            contentDescription = "NO SE ENCONTRARON PRIORIDADES",
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "NO SE ENCONTRARON PRIORIDADES",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                PrioridadesList(
                    prioridades = prioridades,
                    onEditClick = { prioridad ->
                        prioridad.PrioridadId?.let { id ->
                            navController.navigate(Screen.EditarPrioridades(prioridadId = id))
                        }
                    },
                    onDeleteClick = { prioridad ->
                        prioridad.PrioridadId?.let { id ->
                            navController.navigate(Screen.EliminarPrioridades(prioridadId = id))
                        }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.CrearPrioridades)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 60.dp, end = 16.dp),
            containerColor = Lavender,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar Prioridad"
            )
        }
    }
}




@Composable
fun PrioridadesList(
    prioridades: List<PrioridadEntity>,
    onEditClick: (PrioridadEntity) -> Unit,
    onDeleteClick: (PrioridadEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp)
    ) {
        items(prioridades) { prioridad ->
            PrioridadCard(
                prioridad = prioridad,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                index = prioridades.indexOf(prioridad) + 1
            )
        }
    }
}

@Composable
fun PrioridadCard(
    prioridad: PrioridadEntity,
    onEditClick: (PrioridadEntity) -> Unit,
    onDeleteClick: (PrioridadEntity) -> Unit,
    index: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Lavender)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            {
                Text(
                    text = "$index.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
            {
                Text(
                    text = prioridad.Descripcion,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = "${prioridad.DiasCompromiso} días",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = { onEditClick(prioridad) }) {
                    Image(
                        painter = painterResource(id = R.drawable.ico_editar),
                        contentDescription = "Editar",
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { onDeleteClick(prioridad) }) {
                    Image(
                        painter = painterResource(id = R.drawable.ico_eliminar),
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IndexPrioridadesScreenPreview() {
    val navController = rememberNavController()
    IndexPrioridadesScreen(
        onDrawerToggle = {/* */},
        PrioridadesLista = flowOf( /*  */),
        navController = navController
    )
}