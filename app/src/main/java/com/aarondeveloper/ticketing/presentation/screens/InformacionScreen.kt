package com.aarondeveloper.ticketing.presentation.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.ui.theme.Lavender
import com.aarondeveloper.ticketing.ui.theme.White

@Composable
fun InformacionScreen(onDrawerToggle: () -> Unit) {
    val context = LocalContext.current
    val versionName = context.packageManager
        .getPackageInfo(context.packageName, 0).versionName
    
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bkg_informacion),
            contentDescription = "Background Informacion",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Button(
            onClick = onDrawerToggle,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 60.dp, start = 16.dp)
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
                .align(Alignment.TopEnd)
                .padding(top = 130.dp, end = 20.dp)
                .size(150.dp)
                .clip(CircleShape)
                .background(Lavender),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logotipo),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tickering",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = "v $versionName",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Aaron Developer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = (-60).dp, y = (-130).dp)
                .graphicsLayer(rotationZ = -30f)
                .padding(16.dp),
        ) {
            Text(
                text = "Redes Sociales",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ico_facebook),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(45.dp)
                    .offset(x = (-60).dp, y = (-30).dp)
                    .clickable {
                        abrirUrl(context, "https://www.facebook.com/profile.php?id=100008234103134&mibextid=ZbWKwL")
                    },
                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(id = R.drawable.ico_instagram),
                contentDescription = "Instagram",
                modifier = Modifier
                    .size(45.dp)
                    .offset(x = (-45).dp, y = (-65).dp)
                    .clickable {
                        abrirUrl(context, "https://www.instagram.com/aaron_herg")
                    },

                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(id = R.drawable.ico_twitter),
                contentDescription = "Twitter",
                modifier = Modifier
                    .size(45.dp)
                    .offset(x = (-30).dp, y = (-100).dp)
                    .clickable {
                        abrirUrl(context, "https://x.com/")
                    },
                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(id = R.drawable.ico_whatsapp),
                contentDescription = "WhatsApp",
                modifier = Modifier
                    .size(45.dp)
                    .offset(x = (-10).dp, y = (-140).dp)
                    .clickable {
                        abrirUrl(context, "https://wa.me/+18096135721")
                    },
                tint = Color.Unspecified
            )
        }
    }
}


fun abrirUrl(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InformacionScreenPreview() {
    InformacionScreen(onDrawerToggle = { /* */ })
}
