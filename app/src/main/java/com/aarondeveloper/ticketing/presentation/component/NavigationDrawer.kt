import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.aarondeveloper.ticketing.R
import com.aarondeveloper.ticketing.ui.theme.Gray
import com.aarondeveloper.ticketing.ui.theme.Lavender


@Composable
fun NavigationDrawer(
    isVisible: Boolean,
    onItemClick: (String) -> Unit,
    onClose: () -> Unit
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { onClose() }
        )
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(durationMillis = 300)
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .background(Color.White)
        ) {
            Column {
                DrawerHeader(onClose = onClose)
                DrawerBody(
                    items = listOf(
                        MenuItem("Home", Icons.Default.Home),
                        MenuItem("Prioridades", Icons.Default.DateRange),
                        MenuItem("Tickets", Icons.Default.MailOutline),
                        MenuItem("Compartir", Icons.Default.Share),
                        MenuItem("Ajustes", Icons.Default.Settings),
                        MenuItem("Información", Icons.Default.Info)
                    ),
                    onItemClick = {
                        onItemClick(it)
                        onClose()
                    }
                )
            }
        }
    }
}

@Composable
fun DrawerHeader(onClose: () -> Unit) {
    val context = LocalContext.current
    val versionName = context.packageManager
        .getPackageInfo(context.packageName, 0).versionName
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClose() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.dec_menu),
            contentDescription = "Background",
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logotipo),
                contentDescription = "Logotipo",
                modifier = Modifier.size(130.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "v $versionName",
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}


@Composable
fun DrawerBody(
    items: List<MenuItem>,
    onItemClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.8f)
        ) {
            items(items.size) { index ->
                val item = items[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(item.title)
                        }
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = Lavender
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        color = Lavender
                    )
                }
            }
            item {
                Divider(color = Gray)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClick("Salir")
                        }
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Salir",
                        tint = Lavender
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Salir",
                        fontSize = 18.sp,
                        color = Lavender
                    )
                }
            }
        }
    }
}

data class MenuItem(val title: String, val icon: ImageVector)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NavigationDrawerPreview() {
    NavigationDrawer(
        isVisible = true,
        onItemClick = {},
        onClose = {}
    )
}