package com.aarondeveloper.ticketing.presentation.screens.tickets

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.ui.theme.Green
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.Red
import com.aarondeveloper.ticketing.ui.theme.White

@Composable
fun DeleteTicketsScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int?,
    onDrawerToggle: () -> Unit,
    goToTicket: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        if (ticketId != null) {
            viewModel.selectedTicket(ticketId)
        }
    }
    BodyDeletePrioridades(
        uiState = uiState,
        onDrawerToggle = onDrawerToggle,
        goToTicket = goToTicket,
        deleteTicket = viewModel::delete,
    )
}


@Composable
fun BodyDeletePrioridades(
    uiState: UiState,
    onDrawerToggle: () -> Unit,
    goToTicket: () -> Unit,
    deleteTicket: () -> Unit
){


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_tickets_eliminar),
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
                        text = "¿Estás seguro que deseas eliminar este ticket?",
                        style = TextStyle(
                            color = Green,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )

                    uiState.errorMessage?.let { message ->
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
                                deleteTicket()
                                if(uiState.errorMessage == null || uiState.errorMessage == ""){
                                    goToTicket()
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
                                goToTicket()
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
fun DeleteTicketsScreenPreview() {

    DeleteTicketsScreen(
        ticketId = 1,
        onDrawerToggle = {},
        goToTicket = {}
    )
}