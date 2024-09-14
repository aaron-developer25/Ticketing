package com.aarondeveloper.ticketing.presentation.screens.tickets

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.ui.theme.Green
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.White
import java.util.Calendar


@Composable
fun CreateTicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    onDrawerToggle: () -> Unit,
    goToTicket: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BodyCreateTicket(
        uiState = uiState,
        onDrawerToggle = onDrawerToggle,
        onClienteChange = viewModel::onClienteChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onPrioridadChange = viewModel::onPrioridadIdChange,
        onFechaChange = viewModel::onFechaChange,
        goToTicket = goToTicket,
        saveTicket = viewModel::save
    )
}

@Composable
fun BodyCreateTicket(
    uiState: UiState,
    onDrawerToggle: () -> Unit,
    onClienteChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onPrioridadChange: (Int) -> Unit,
    onFechaChange: (String) -> Unit,
    goToTicket: () -> Unit,
    saveTicket: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier.zIndex(1f)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.bkg_tickets_nuevo),
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
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(1f)
        ) {
            LazyColumn {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 300.dp)
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
                                        .border(
                                            BorderStroke(2.dp, Lavender),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                ) {
                                    BasicTextField(
                                        value = uiState.cliente ?: "",
                                        onValueChange = onClienteChange,
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
                                            visible = uiState.cliente.isNullOrEmpty(),
                                            enter = fadeIn(),
                                            exit = fadeOut(),
                                            modifier = Modifier
                                                .offset(y = 14.dp)
                                        ) {
                                            Text(
                                                text = "Cliente",
                                                color = Color.Gray,
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            BorderStroke(2.dp, Lavender),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                ) {
                                    BasicTextField(
                                        value = uiState.asunto ?: "",
                                        onValueChange = onAsuntoChange,
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
                                            visible = uiState.asunto.isNullOrEmpty(),
                                            enter = fadeIn(),
                                            exit = fadeOut(),
                                            modifier = Modifier
                                                .offset(y = 14.dp)
                                        ) {
                                            Text(
                                                text = "Asunto",
                                                color = Color.Gray,
                                                fontSize = 16.sp
                                            )
                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            BorderStroke(2.dp, Lavender),
                                            shape = RoundedCornerShape(10.dp)
                                        )
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

                                CustomSelectNuevo(uiState, onPrioridadChange)
                                CustomDatePickerNuevo(uiState, onFechaChange)

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
                                        saveTicket()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Green
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Guardar",
                                        tint = White,
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                    )
                                    Text(
                                        text = "Guardar",
                                        color = White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
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
                                goToTicket()
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CustomSelectNuevo(
    uiState: UiState,
    onPrioridadChange: (Int) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedPrioridad = uiState.prioridades.find { it.PrioridadId == uiState.prioridadId }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(BorderStroke(2.dp, Lavender), shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedPrioridad?.Descripcion ?: "",
                style = TextStyle(
                    color = Green,
                    fontSize = 16.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
                    .offset(x = 8.dp),
                tint = Color.Gray
            )
        }


        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .width(265.dp)
                .background(Color.White)
                .border(BorderStroke(2.dp, Lavender), shape = RoundedCornerShape(10.dp))
                .align(Alignment.TopStart)
        ) {
            uiState.prioridades.forEach { prioridad ->
                DropdownMenuItem(
                    onClick = {
                        onPrioridadChange(prioridad.PrioridadId ?: 0)
                        expanded.value = false
                        Toast.makeText(context, prioridad.Descripcion, Toast.LENGTH_SHORT).show()
                    },
                    text = {
                        Text(text = prioridad.Descripcion)
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = selectedPrioridad == null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp)
                .offset(y = 14.dp)
        ) {
            Text(
                text = "Prioridad",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun CustomDatePickerNuevo(
    uiState: UiState,
    onFechaChange: (String) -> Unit
) {
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val selectedDate = uiState.fecha ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(BorderStroke(2.dp, Lavender), shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded.value = true
                }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedDate.isEmpty()) "" else selectedDate,
                style = TextStyle(
                    color = if (selectedDate.isEmpty()) Color.Gray else Green,
                    fontSize = 16.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Calendar Icon",
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
                    .offset(x = 8.dp),
                tint = Color.Gray
            )
        }

        AnimatedVisibility(
            visible = selectedDate.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp)
                .offset(y = 14.dp)
        ) {
            Text(
                text = "Fecha",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }

        LaunchedEffect(expanded.value) {
            if (expanded.value) {
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selected = "$dayOfMonth/${month + 1}/$year"
                        onFechaChange(selected)
                        expanded.value = false
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.setOnDismissListener {
                    expanded.value = false
                }
                datePickerDialog.show()
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateTicketScreenPreview() {
    CreateTicketScreen(
        onDrawerToggle = {},
        goToTicket = {}
    )
}
