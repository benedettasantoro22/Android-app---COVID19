package com.example.covid_19evaluationrisk.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid_19evaluationrisk.CovidTopAppBar
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider
import kotlinx.coroutines.launch


object HomepageDestination: NavigationDestination{
    override val route = "home"
    override val titleRes = R.string.home

}





@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomepageScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    ){


    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    //val homeUiState by homeViewModel.homeUiState.collectAsState()

    val items = listOf(

        Menu(
            RoomDestination.route,
            title = stringResource(id = R.string.new_evaluation)
        ),

        Menu(
            HistoryDestination.route,
            stringResource(id = R.string.history)
        ),

        )
    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CovidTopAppBar(
                title = stringResource(id = HomepageDestination.titleRes),
                canNavigateBack = false,
                navigateUp = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            Drawer(items = items, navController = navHostController)
        }


    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()

        ) {


            item {
                Text(
                    text = stringResource(id = R.string.welcome_message) + "\n" + viewModel.user!!.displayName.toString(),
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
            }

            item {
                Spacer(modifier = Modifier.height(35.dp))
            }

            item {
                Image(
                    painter = painterResource(R.drawable.sars_cov_2_without_background),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(0.22f)
                )
            }

            item {
                Spacer(modifier = Modifier.height(35.dp))
            }


            item {
                Button(
                    onClick = {


                        navHostController.navigate(RoomDestination.route)
                        //database(username = viewModel.user!!.displayName.toString(),uid = viewModel.user.uid)

                       },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),


                    ) {
                    Text(
                        text = stringResource(id = R.string.new_evaluation),
                        style = MaterialTheme.typography.button,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }

            }




            item {
                Spacer(modifier = Modifier.height(35.dp))
            }


            item {

                Button(
                    onClick = {

                      //  setUsername(viewModel.user!!.displayName.toString())
                        navHostController.navigate(HistoryDestination.route)

                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),


                    ) {
                    Text(
                        text = stringResource(id = R.string.history),
                        style = MaterialTheme.typography.button,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }



            }



        }
    }



}