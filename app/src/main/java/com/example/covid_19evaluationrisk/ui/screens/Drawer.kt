package com.example.covid_19evaluationrisk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider

data class Menu(

    val route: String,
    val title: String
)





@Composable
fun Drawer(
    items: List<Menu>,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)) {


    val context = LocalContext.current

    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.sars_cov_2_without_background),
            contentDescription = "Drawer",
            modifier = Modifier.fillMaxWidth(0.25f)
        )
        items.forEach { item ->
            Spacer(Modifier.height(24.dp))

            Text(text = item.title, style = MaterialTheme.typography.h4, modifier = modifier.clickable { navController.navigate(item.route) })
        }

        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.log),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.clickable {
                viewModel.userLogOut(context = context)
                navController.navigate(HomeDestination.route)
            }
        )
    }
}