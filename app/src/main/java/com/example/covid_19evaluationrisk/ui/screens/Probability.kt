package com.example.covid_19evaluationrisk.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid_19evaluationrisk.CovidTopAppBar
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong


object ProbabilityDestination: NavigationDestination {
    override val route = "prob"
    override val titleRes = R.string.prob
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProbabilityScreen(

    percentage: String,
    navHostController: NavHostController,
    goHome: () -> Unit,
    goToHistory:() -> Unit,
    //setUsername: (String) -> Unit,
    setDate: (String) -> Unit,
    //viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    saveEvaluation: () -> Unit

){



    Scaffold(
        topBar = {
            CovidTopAppBar(
                title = stringResource(id = ProbabilityDestination.titleRes),
                canNavigateBack = true,
                navigateUp = { navHostController.popBackStack()}
            )
        }


    ){


        var bool by remember { mutableStateOf(true)    }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Image(painter = painterResource(R.drawable.sars_cov_2_without_background), contentDescription = "", modifier = Modifier.fillMaxWidth(0.25f))

            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                bool = !bool
            }) {
                Text(text = stringResource(id = R.string.prob_comp))
            }

            Spacer(modifier = Modifier.height(10.dp))
            if (!bool){
                CircularProgressbar1(percentage )
            }

            Button(onClick = goHome) {
                Text(text = stringResource(id = R.string.home))
            }

            Button(onClick =  {
                //setUsername(viewModel.user!!.displayName.toString())
                setDate((LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")).toString()))
          //      Log.d("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")).toString())
                saveEvaluation()
            }) {
                Text(text = stringResource(id = R.string.details))
            }


            Button(
                onClick = goToHistory,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),


                ) {
                Text(text = stringResource(id = R.string.history) )
            }





        }
    }
}






@Composable
fun CircularProgressbar1(
    percentage: String,
    size: Dp = 260.dp,
    foregroundIndicatorColor: Color = MaterialTheme.colors.surface,
    shadowColor: Color = MaterialTheme.colors.onSurface,
    indicatorThickness: Dp = 24.dp,
    dataUsage: Float = 60f,
    animationDuration: Int = 3000,
    dataTextStyle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.font ,FontWeight.Bold)),
        fontSize = MaterialTheme.typography.h3.fontSize
    ),

) {
    // It remembers the data usage value
    var dataUsageRemember by remember {
        mutableStateOf(-1f)
    }

    // This is to animate the foreground indicator
    val dataUsageAnimate = animateFloatAsState(
        targetValue = percentage.toFloat().times(100),
        animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        dataUsageRemember = dataUsage
    }

    Box(
        modifier = Modifier
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
        ) {
            // For shadow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(shadowColor, Color.Blue),
                    center = Offset(x = this.size.width / 2, y = this.size.height / 2),
                    radius = this.size.height / 2
                ),
                radius = this.size.height / 2,
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )

            // This is the white circle that appears on the top of the shadow circle
            drawCircle(
                color = Color.White,
                radius = (size / 2 - indicatorThickness).toPx(),
                center = Offset(x = this.size.width / 2, y = this.size.height / 2)
            )

            // Convert the dataUsage to angle
            val sweepAngle = (dataUsageAnimate.value) * 360 / 100

            // Foreground indicator
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }

        // Display the data usage value
        DisplayText(
            animateNumber = (percentage.toFloat().times(100.00)).toFloat(),
            dataTextStyle = dataTextStyle,
            //remainingTextStyle = remainingTextStyle
        )
    }


}

@Composable
private fun DisplayText(
    animateNumber: Float,
    dataTextStyle: TextStyle,
    //remainingTextStyle: TextStyle
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text that shows the number inside the circle
        Text(
            text = String.format(Locale.US,"%.2f",animateNumber) + " %",
            style = dataTextStyle
        )


    }
}
