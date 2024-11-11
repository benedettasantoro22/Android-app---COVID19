package com.example.covid_19evaluationrisk.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.screens.*
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider
import com.example.covid_19evaluationrisk.ui.viewmodel.EvaluationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CovidNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    evaluationViewModel: EvaluationViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by evaluationViewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    //var startDestination by remember { mutableStateOf(HomeDestination.route) }

   // var date by remember{
     //   mutableStateOf()}


    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route){
            Home(navHostController = navController)
        }
        composable(route = ForgotDestination.route){
            ForgotPassword(navHostController = navController)
        }
        composable(route= CreateDestination.route){
            CreateAccount(navController = navController)
        }
        composable(route = HomepageDestination.route) {
            HomepageScreen(
                navHostController = navController,


                )
        }
        composable(route = RoomDestination.route) {
            RoomScreen(
                navigateForward = {
                    navController.navigate(OccupantDestination.route)
                  //  firebase.getReference("users").child(viewModel.user!!.uid).setValue(viewModel.user!!.displayName)

                                  },
                navigateUp = { navController.popBackStack() },
                onVolumeChange = { evaluationViewModel.setRoomVolume(it) },
                onVentilationChange = { evaluationViewModel.setRoomVentilation(it) },
                onHumidityChange = { evaluationViewModel.setHumidity(it) },
                onCheckChange = {evaluationViewModel.setHEPA(it)}


            )
        }
        composable(route = OccupantDestination.route) {
            OccupantsScreen(
                navigateForward = {
                    evaluationViewModel.computeProbability()
                    navController.navigate(ProbabilityDestination.route)
                                  },
                navigateBack = {navController.popBackStack()},
                onInfectiousChange = {evaluationViewModel.setIOccupants(it)},
                onMaskIChange = {evaluationViewModel.setIMask(it)},
                onSetActivity = {evaluationViewModel.setActivity(it)},
                onSusceptibleChange = {evaluationViewModel.setSOccupants(it)},
                onMaskSChange = {evaluationViewModel.setSMask(it)},
                onTimeChange = {evaluationViewModel.setTime(it)}
            )

        }


        composable(route = ProbabilityDestination.route) {
            ProbabilityScreen(
                percentage =  uiState.infectionProbability,
                navHostController = navController,
                goHome = {navController.navigate(HomepageDestination.route)},
                goToHistory = {navController.navigate(HistoryDestination.route)},
                setDate = {
                    evaluationViewModel.setDate(it)
                  //  firebase.getReference("users").child(viewModel.user!!.uid).child("Evaluation").child("date").setValue(it)
                },
                //setUsername = {evaluationViewModel.setUsername(it)},
                saveEvaluation = {
                    Log.d("uid",viewModel.user!!.uid + "     " + viewModel.user!!.displayName)
                    coroutineScope.launch {

                        //firebase.getReference("users/"+ viewModel.user!!.displayName +"/Evaluations").child(uiState.date).setValue(uiState.toEvaluation())


                        //firebase.getReference("users/"+ viewModel.user!!.displayName).push().child("/Evaluation").setValue(uiState.toEvaluation())
                        evaluationViewModel.saveEvaluation(uiState)
                        Log.d("insert", uiState.toString())
                    }
                    }
            )
        }
        
        
        
        composable(route = HistoryDestination.route){
            HistoryScreen(
                navHostController = navController,
                navToDetailsScreen = {navController.navigate("${ItemDetailsDestination.route}/${it}" )}
                //navBack = { navController.popBackStack() }

            )
        }


        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg){
                type = NavType.StringType
            })
            ){
            DetailsScreen( goBack = {navController.popBackStack()})
        }
    }
}