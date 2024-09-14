package com.aarondeveloper.ticketing.presentation.screens.perioridades

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.ui.theme.Green
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.White

@Composable
fun CreatePrioridadesScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    onDrawerToggle: () -> Unit,
    goToPrioridad: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BodyCreatePrioridades(
        uiState = uiState,
        onDescripcionChange = viewModel::onDescripcionChange,
        onDiasCompromisoChange = viewModel::onDiasCompromisoChange,
        onDrawerToggle = onDrawerToggle,
        goToPrioridad = goToPrioridad,
        savePrioridad = viewModel::save
    )
}

@Composable
fun BodyCreatePrioridades(
    uiState: UiState,
    onDrawerToggle: () -> Unit,
    onDescripcionChange: (String) -> Unit,
    onDiasCompromisoChange: (String) -> Unit,
    goToPrioridad: () -> Unit,
    savePrioridad: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_prioridades_nueva),
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
                .padding(top = 160.dp)
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
                            value = uiState.descripcion ?: "",
                            onValueChange = onDescripcionChange,
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
                                visible = uiState.descripcion.isNullOrEmpty(),
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
                            value = uiState.diascompromiso ?: "",
                            onValueChange = onDiasCompromisoChange,
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
                                visible = uiState.diascompromiso.isNullOrEmpty(),
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

                    uiState.errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            savePrioridad()
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
                                contentDescription = "Guardar",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Guardar",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            Image(
                painter = painterResource(id = R.drawable.ico_emojis_emocionado),
                contentDescription = "Imagen Prioridad",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-48).dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(3.dp, Lavender, CircleShape)
            )

            LaunchedEffect(uiState.guardado) {
                if (uiState.guardado == true) {
                    goToPrioridad()
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreatePrioridadesScreen() {
    CreatePrioridadesScreen(
        onDrawerToggle = {},
        goToPrioridad = {}
    )
}
