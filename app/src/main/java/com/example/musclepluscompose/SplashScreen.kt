package com.example.musclepluscompose

import android.content.Intent
import android.graphics.Color.alpha
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.musclepluscompose.ui.theme.MuscleBlue
import kotlinx.coroutines.delay

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen"){
        composable(route = Screen.SplashScreen.route) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = "HOME SCREEN", color = Color.White)
            }
        }
    }
}

@Composable
fun AnimatedSplashScreen(navController: NavController){
    //val mContext = LocalContext.current
    var startAnimation by remember { mutableStateOf(false)}
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            }
        )
    )

    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(3000L)
        navController.popBackStack()
        navController.navigate(route = Screen.Home.route)
        //mContext.startActivity(Intent(mContext, MainActivity::class.java))
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(MuscleBlue)
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_round),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .alpha(alpha = alpha)
        )
    }
}

// For displaying preview in
// the Android Studio IDE emulator

@Composable
@Preview
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}