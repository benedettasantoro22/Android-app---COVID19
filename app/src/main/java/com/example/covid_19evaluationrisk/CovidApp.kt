package com.example.covid_19evaluationrisk

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.covid_19evaluationrisk.ui.navigation.CovidNavHost

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CovidApp(navHostController: NavHostController = rememberNavController()){
    CovidNavHost(navController = navHostController)
}






@Composable
fun CovidTopAppBar(title:String, canNavigateBack: Boolean, modifier: Modifier = Modifier, navigateUp: () -> Unit = {}) {

    if (canNavigateBack) {

        TopAppBar(
            title = { Text(title) },
            modifier = modifier,
            navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        )
    } else {
        TopAppBar(title = { Text(title) }, modifier = modifier, navigationIcon = {  IconButton(onClick = navigateUp) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu"
            )
        }    })
    }

}