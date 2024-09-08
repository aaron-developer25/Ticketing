package com.aarondeveloper.ticketing.presentation.screens.perioridades

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.data.local.dao.PrioridadDao
import com.aarondeveloper.ticketing.data.local.entities.PrioridadEntity
import com.aarondeveloper.ticketing.presentation.navigation.Screen
import com.aarondeveloper.ticketing.ui.theme.Green
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.White
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun EditPrioridadesScreen(
    onDrawerToggle: () -> Unit,
    navController: NavController,
    prioridadDao: PrioridadDao? = null,
    prioridadId: Int?
) {
    val coroutineScope = rememberCoroutineScope()
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var validationMessage by remember { mutableStateOf<String?>(null) }

    var prioridad by remember { mutableStateOf<PrioridadEntity?>(null) }

    LaunchedEffect(prioridadId) {
        if (prioridadDao != null && prioridadId != null) {
            prioridadDao.find(prioridadId)?.let { p ->
                prioridad = p
                descripcion = p.Descripcion
                diasCompromiso = p.DiasCompromiso?.toString() ?: ""
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_prioridades_editar),
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
                .align(Alignment.Center)
                .padding(top = 150.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .border(3.dp, Lavender, shape = RoundedCornerShape(30.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(30.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .border(BorderStroke(2.dp, Lavender), shape = RoundedCornerShape(10.dp))
                    ) {
                        BasicTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            textStyle = TextStyle(
                                color = Green,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 22.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 8.dp)
                        ) {
                            this@Column.AnimatedVisibility(
                                visible = descripcion.isEmpty(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                                modifier = Modifier
                                    .offset(y = 14.dp)
                            ) {
                                Text(
                                    text = "Descripción",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(2.dp, Lavender), shape = RoundedCornerShape(10.dp))
                    ) {
                        BasicTextField(
                            value = diasCompromiso,
                            onValueChange = { diasCompromiso = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            textStyle = TextStyle(
                                color = Green,
                                fontSize = 16.sp,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 22.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 8.dp)
                        ) {
                            this@Column.AnimatedVisibility(
                                visible = diasCompromiso.isEmpty(),
                                enter = fadeIn(),
                                exit = fadeOut(),
                                modifier = Modifier
                                    .offset(y = 14.dp)
                            ) {
                                Text(
                                    text = "Días de Compromiso",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    validationMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val dias = diasCompromiso.toIntOrNull()
                                if (descripcion.isBlank() || dias == null || dias <= 0) {
                                    validationMessage = "Por favor, completa todos los campos correctamente."
                                }
                                else {
                                    val existingPrioridad = prioridadDao?.findByDescripcion(descripcion)
                                    if (existingPrioridad != null && existingPrioridad.PrioridadId != prioridadId) {
                                        validationMessage = "Ya existe una prioridad con esta descripción."
                                    } else {
                                        validationMessage = null
                                        val prioridadToUpdate = PrioridadEntity(
                                            PrioridadId = prioridadId ?: 0,
                                            Descripcion = descripcion,
                                            DiasCompromiso = dias
                                        )
                                        prioridadDao?.save(prioridadToUpdate)
                                        navController.navigate(Screen.ControlPanelPrioridades)
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Actualizar",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Actualizar",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ico_emojis_sorprendido),
                contentDescription = "Imagen Prioridad",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-48).dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(3.dp, Lavender, CircleShape)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditPrioridadesScreenPreview() {
    val navController = rememberNavController()

    EditPrioridadesScreen(
        onDrawerToggle = { /* */ },
        navController = navController,
        prioridadDao = null,
        prioridadId = 1
    )
}
