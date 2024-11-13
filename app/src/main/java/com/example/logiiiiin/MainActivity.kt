package com.example.logiiiiin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logiiiiin.ui.theme.DeepPink
import com.example.logiiiiin.ui.theme.SoftPink

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf("Login") }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(SoftPink, DeepPink)))
    ) {
        when (currentScreen) {
            "Login" -> LoginScreen(
                onLoginSuccess = { currentScreen = "Home" },
                onForgotPasswordClick = { currentScreen = "ResetPassword" },
                onSignUpClick = { currentScreen = "SignUp" }
            )
            "Home" -> HomeScreen(
                onLogoutClick = { currentScreen = "Login" },
                onProductClick = { product ->
                    selectedProduct = product
                    currentScreen = "ProductDetail"
                }
            )
            "ProductDetail" -> selectedProduct?.let { product ->
                ProductDetailScreen(product = product, onBackClick = { currentScreen = "Home" })
            }
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen", fontSize = 24.sp, color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLoginSuccess,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Login", color = DeepPink)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onForgotPasswordClick) {
            Text("Forgot Password?", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onSignUpClick) {
            Text("Sign Up", color = Color.White)
        }
    }
}

@Composable
fun HomeScreen(onLogoutClick: () -> Unit, onProductClick: (Product) -> Unit) {
    val productList = listOf(
        Product(name = "Lipstick", price = 10.0, description = "Le rouge à lèvres changeant de couleur ELECTRIC GLOW d'essence réagit à la valeur pH individuelle de la peau des lèvres et, par conséquent, change de couleur.", imageUrl = R.drawable.rouge),
        Product(name = "Highlighter", price = 20.0, description = "Un enlumineur poudre innovant qui apporte immédiatement à la peau une brillance cristal, pour offrir facilement un résultat éclatant, visiblement naturel et durable.\n", imageUrl = R.drawable.highlighter),
        Product(name = "Mascara", price = 30.0, description = "Cils déployés et volume extrême, le mascara est l’atout glamour pour un regard de\n" +
                "biche. Noir intense, vert sapin ou marron glacé, vos yeux se parent de couleurs pour\n" +
                "un look sophistiqué", imageUrl = R.drawable.mascara),
        Product(name = "Foundation", price = 40.0, description = "Le secret d’un beau maquillage est un teint parfait. Fluide ou compact, le fond de teint assure une peau parfaite instantanément.", imageUrl = R.drawable.foundation) ,
        Product(name = "blush", price = 40.0, description = "Réveillez votre teint avec un joli blush ! Rose ou corail, il sublime votre teint tout en lui\n" +
                "offrant lumière et chaleur.", imageUrl = R.drawable.blush)

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(productList) { product ->
                ProductCard(product = product, onClick = { onProductClick(product) })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink)
        ) {
            Text("Logout", fontSize = 20.sp, color = Color.Black)
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = product.name,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${product.price} €",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ProductDetailScreen(product: Product, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = product.name,
            fontSize = 24.sp,
            color = DeepPink,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = product.imageUrl),
            contentDescription = product.name,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${product.price} €",
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = product.description,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(containerColor = SoftPink)
        ) {
            Text("Back", color = Color.Black)
        }
    }
}

data class Product(
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: Int
)
