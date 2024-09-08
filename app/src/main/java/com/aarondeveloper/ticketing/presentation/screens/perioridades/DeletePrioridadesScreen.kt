package com.aarondeveloper.ticketing.presentation.screens.perioridades

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.aarondeveloper.ticketing.ui.theme.Red
import com.aarondeveloper.ticketing.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun DeletePrioridadesScreen(
    onDrawerToggle: () -> Unit,
    navController: NavController,
    prioridadDao: PrioridadDao? = null,
    prioridadId: Int?
) {
    val coroutineScope = rememberCoroutineScope()
    var validationMessage by remember { mutableStateOf<String?>(null) }
    var prioridad by remember { mutableStateOf<PrioridadEntity?>(null) }

    LaunchedEffect(prioridadId) {
        if (prioridadDao != null && prioridadId != null) {
            prioridadDao.find(prioridadId)?.let { p ->
                prioridad = p
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_prioridades_eliminar),
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
                    Text(
                        text = "¿Estás seguro que deseas eliminar esta prioridad?",
                        style = TextStyle(
                            color = Green,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )

                    validationMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    if (prioridad != null) {
                                        prioridadDao?.delete(prioridad!!)
                                        navController.navigate(Screen.ControlPanelPrioridades)
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Green
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Confirmar",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Button(
                            onClick = {
                                navController.navigate(Screen.ControlPanelPrioridades)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Red
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Cancelar",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ico_emojis_triste),
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
fun DeletePrioridadesScreenPreview() {
    val navController = rememberNavController()

    DeletePrioridadesScreen(
        onDrawerToggle = { /* */ },
        navController = navController,
        prioridadDao = null,
        prioridadId = 1
    )
}
