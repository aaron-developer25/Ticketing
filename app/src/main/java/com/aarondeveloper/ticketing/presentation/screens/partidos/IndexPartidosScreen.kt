package com.aarondeveloper.ticketing.presentation.screens.partidos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.data.local.entities.PartidoEntity
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.White

@Composable
fun IndexPartidosScreen(
    viewModel: PartidoViewModel = hiltViewModel(),
    onDrawerToggle: () -> Unit,
    goToPartido: () -> Unit,
    createPartido: () -> Unit,
    editPartido: (Int) -> Unit,
    deletePartido: (Int) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_partidos_control),
            contentDescription = "Background Principal",
            modifier = Modifier.fillMaxSize(),
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
                modifier = Modifier.padding(horizontal = 8.dp)
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
            if (uiState.partidos.isEmpty()) {
                MensajePersonalizado()
            } else {
                PartidosList(
                    partidos = uiState.partidos,
                    onEditClick = { partido ->
                        partido.partidoId?.let { id ->
                            editPartido(id)
                        }
                    },
                    onDeleteClick = { partido ->
                        partido.partidoId?.let { id ->
                            deletePartido(id)
                        }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = createPartido,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 60.dp, end = 16.dp),
            containerColor = Lavender,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar Partido"
            )
        }

        uiState.errorMessage?.let { message ->
            Text(
                text = message,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun PartidosList(
    partidos: List<PartidoEntity>,
    onEditClick: (PartidoEntity) -> Unit,
    onDeleteClick: (PartidoEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp)
    ) {
        items(partidos) { partido ->
            PartidoCard(
                partido = partido,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                index = partidos.indexOf(partido) + 1
            )
        }
    }
}

@Composable
fun PartidoCard(
    partido: PartidoEntity,
    onEditClick: (PartidoEntity) -> Unit,
    onDeleteClick: (PartidoEntity) -> Unit,
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
                modifier = Modifier.padding(start = 16.dp)
            ) {
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
            ) {
                Text(
                    text = partido.titulo + " (" + partido.descripcion + ")",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = { onEditClick(partido) }) {
                    Image(
                        painter = painterResource(id = R.drawable.ico_editar),
                        contentDescription = "Editar",
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { onDeleteClick(partido) }) {
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

@Composable
fun MensajePersonalizado() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ico_busqueda),
                contentDescription = "NO SE ENCONTRARON PARTIDOS",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "NO SE ENCONTRARON PARTIDOS",
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IndexPartidosScreenPreview() {
    IndexPartidosScreen(
        viewModel = hiltViewModel(),
        onDrawerToggle = {},
        goToPartido = {},
        createPartido = {},
        editPartido = {},
        deletePartido = {}
    )
}
