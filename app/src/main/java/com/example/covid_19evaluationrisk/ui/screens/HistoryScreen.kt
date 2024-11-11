package com.example.covid_19evaluationrisk.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid_19evaluationrisk.CovidTopAppBar
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.firebase.DataState
import com.example.covid_19evaluationrisk.firebase.Evaluation
import com.example.covid_19evaluationrisk.firebase.FirebaseViewModel
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object HistoryDestination: NavigationDestination{

    override val route = "history"
    override val titleRes = R.string.history

}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(
    navToDetailsScreen: (String) -> Unit = {},
    navHostController: NavHostController,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
   // homepageViewModel: HomepageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    firebaseViewModel: FirebaseViewModel = viewModel(factory = AppViewModelProvider.Factory),









    ) {

    val itemList = listOf(


        Menu(
            HomepageDestination.route,
            title = stringResource(id = R.string.home)
        ),

        Menu(
            RoomDestination.route,
            stringResource(id = R.string.new_evaluation)
        ),

        )


    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
   // val listState = rememberLazyListState()

   // val homeUiState by homepageViewModel.homeUiState.collectAsState()
    
    
    
  
    /*val firebaseUiState = firebaseViewModel.response.value

    when(firebaseUiState){
        is DataState.Loading -> {

        }
        is DataState.Success -> {
            Log.d("firebase",firebaseUiState.data.toString())
        }

        is DataState.Failure -> {
        }

        else -> {

        }

    }


*/
    Scaffold(

        scaffoldState = scaffoldState,
        topBar = {
            CovidTopAppBar(
                title = stringResource(id = HistoryDestination.titleRes),
                canNavigateBack = false,
                navigateUp = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            Drawer(items = itemList, navController = navHostController)
        },



        //  floatingActionButtonPosition = FabPosition.End
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.sars_cov_2_without_background),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(0.22f)
                )
                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = viewModel.user!!.displayName.toString(),
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    //fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Start
                )


            }





            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = stringResource(id = R.string.message),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
                        .background(MaterialTheme.colors.surface),
                    textAlign = TextAlign.Justify,

                    )
            }
            //HistoryBody(itemList = homeUiState.list, onClick = navToDetailsScreen)

            when(val response = firebaseViewModel.response.value){
                is DataState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is DataState.Success -> {

                    LazyColumn {
                        items(response.data.size) {
                            //Log.d("", response.keys[it].toString() +" "+ response.data[it].date + " " +it.toString())
                            val ev = response.data.sortedByDescending { LocalDateTime.parse(it.date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) }[it]
                            HistoryItem(
                                evaluationNumber = stringResource(id = R.string.`val`) +  "  #${it + 1}",
                                evaluation = ev,
                                onItemClick = {
                                    navToDetailsScreen(it.id!!)
                                    //Log.d("", response.keys.toString())




                                })
                        }
                    }
                }

                is DataState.Failure -> {

                }

                else -> {

                }

            }
            //prova(firebaseViewModel = firebaseViewModel)

            /*  LazyColumn(
                  state = listState,
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center,
                  modifier = Modifier
                      .fillMaxSize()
              ) {


                 items(homeUiState.list.size) { item ->
                      HistoryItem(
                          evaluationNumber = stringResource(id = R.string.`val`) +  "#${homeUiState.list.size - item}",
                          covidEvaluation = homeUiState.list[item],
                          onItemClick = { navToDetailsScreen(it.id) })
                      prova(firebaseViewModel = firebaseViewModel)
                  }


              }
  */
        }


    }


}









@Composable
private fun HistoryItem(evaluationNumber: String, evaluation: Evaluation, onItemClick: (Evaluation) -> Unit, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 55.dp, end = 55.dp)
            .clickable { onItemClick(evaluation) },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 50.dp
    ) {


        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {

            Text(
                text = evaluationNumber,
                color = MaterialTheme.colors.onBackground,

                )

            Text(
                text = evaluation.date!!,
                color = MaterialTheme.colors.onBackground,

                )



        }
    }

}




/*

@Composable
fun prova(firebaseViewModel: FirebaseViewModel){

    when(val response = firebaseViewModel.response.value){
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DataState.Success -> {

            LazyColumn {
                items(response.data.size) {
             //       Log.d("", response.keys[it])
                    HistoryItem(
                        evaluationNumber = stringResource(id = R.string.`val`) +  "  #${it + 1}",
                        evaluation = response.data[it],
                        onItemClick = {
                            //Log.d("", response.keys.toString())




                    })
                }
            }
        }

        is DataState.Failure -> {
        }

        else -> {

        }

    }
    
    
    
}




@Composable
private fun HistoryItem( evaluationNumber: String, covidEvaluation: CovidEvaluation, onItemClick: (CovidEvaluation) -> Unit, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 55.dp, end = 55.dp)
            .clickable { onItemClick(covidEvaluation) },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 50.dp
    ) {


        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
        ) {

            Text(
                text = evaluationNumber,
                color = MaterialTheme.colors.onBackground,

                )

            Text(
                text = covidEvaluation.date,
                color = MaterialTheme.colors.onBackground,

                )

        }
    }

}


*/










