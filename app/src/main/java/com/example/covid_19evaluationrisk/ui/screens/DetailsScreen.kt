package com.example.covid_19evaluationrisk.ui.screens

import android.annotation.SuppressLint
import android.graphics.*
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.covid_19evaluationrisk.CovidTopAppBar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.firebase.DetailFirebaseViewModel
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider
import java.util.*


object ItemDetailsDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.ev
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    viewModel: DetailFirebaseViewModel = viewModel(factory = AppViewModelProvider.Factory),
    //viewModel: FirebaseViewModel
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    goBack: () -> Unit,


    ) {

    val uiState = viewModel.eval.collectAsState()

    //val uiState = viewModel.dataFetch(ItemDetailsDestination.route)
    var relativeHumidity by remember { mutableStateOf("") }

    var maskI by remember { mutableStateOf("") }
    var maskS by remember { mutableStateOf("") }
    var activity by remember { mutableStateOf("") }
    var prob by remember { mutableStateOf(0.toDouble())   }



   // var prob = uiState.value.evaluationDetails.infectionProbability//.toDouble().times(100)

    when (uiState.value.evaluationDetails.relativeHumidity) {
        "0.00367323" -> {
            relativeHumidity = "21.0"
        }
        "0.15772872" -> {
            relativeHumidity = "40.0"
        }
        "0.4016" -> {
            relativeHumidity = "70.0"
        }
    }

    when (uiState.value.evaluationDetails.maskI) {
        "0.53" -> {
            maskI = stringResource(id = R.string.surgical)
        }
        "0.9" -> {
            maskI = stringResource(id = R.string.ffp2)
        }
        "0" -> {
            maskI = stringResource(id = R.string.no)
        }
    }

    when (uiState.value.evaluationDetails.maskS) {
        "0.49" -> {
            maskS = stringResource(id = R.string.surgical)
        }
        "0.9" -> {
            maskS = stringResource(id = R.string.ffp2)
        }
        "0" -> {
            maskS = stringResource(id = R.string.no)
        }
    }

    when (uiState.value.evaluationDetails.activity) {
        "1.521" -> {
            activity = stringResource(id = R.string.breath)
        }
        "44.46" -> {
            activity = stringResource(id = R.string.speak)
        }
        "105.3" -> {
            activity = stringResource(id = R.string.sing)
        }
    }



    if(uiState.value.evaluationDetails.infectionProbability.isNotEmpty()){
        prob = uiState.value.evaluationDetails.infectionProbability.toDouble().times(100)
    }

    //prob = uiState.value.evaluationDetails.infectionProbability.toDoubleOrNull()!!.times(100)
    Log.d("", prob.toString())




    val superscript = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 8.sp,
        fontWeight = FontWeight.SemiBold
    )
    Scaffold(
        topBar = {
            CovidTopAppBar(
                title = stringResource(id = ItemDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = goBack
            )
        },


        ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxSize()
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
                Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = loginViewModel.user!!.displayName.toString(),
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    //fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.height(40.dp))


            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = stringResource(id = R.string.date)+":",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = uiState.value.evaluationDetails.date,
                    fontSize = 18.sp
                )
            }

            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 20.dp)
            ) {

                stickyHeader {
                    Text(
                        text = stringResource(id = R.string.room),
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth(),
                        fontSize = 20.sp
                    )
                }

                item {

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start

                        ) {

                            Text(
                                text = buildAnnotatedString {
                                    append(stringResource(id = R.string.volume) + "[m")
                                    withStyle(superscript) {
                                        append("3")
                                    }
                                    append("]:")
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = uiState.value.evaluationDetails.volume,
                                fontSize = 18.sp
                            )


                        }
                        Row(
                            horizontalArrangement = Arrangement.Start

                        ) {

                            Text(
                                text = stringResource(id = R.string.ventilation),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = uiState.value.evaluationDetails.ventilation,
                                fontSize = 18.sp
                            )

                        }


                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(
                                text = stringResource(id = R.string.humidity)+":",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = relativeHumidity,
                                fontSize = 18.sp
                            )


                        }
                    }


                }

                stickyHeader {
                    Text(
                        text = stringResource(id = R.string.occupants),
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth(),
                        fontSize = 20.sp
                    )

                }


                item {

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start

                        ) {

                            Text(
                                text = stringResource(id = R.string.i)+":",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = uiState.value.evaluationDetails.infectious,
                                fontSize = 18.sp
                            )


                        }
                        Row(
                            horizontalArrangement = Arrangement.Start

                        ) {

                            Text(
                                text = stringResource(id = R.string.s)+":",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = uiState.value.evaluationDetails.susceptible,
                                fontSize = 18.sp
                            )

                        }


                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Text(
                                text = stringResource(id = R.string.time),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = uiState.value.evaluationDetails.time,
                                fontSize = 18.sp
                            )


                        }


                    }


                }

                stickyHeader {
                    Text(
                        text = stringResource(id = R.string.activity)+":",
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth(),
                        fontSize = 20.sp
                    )
                }


                item {
                    Text(
                        text = activity,
                        fontSize = 18.sp
                    )
                }

                stickyHeader {
                    Text(
                        text = stringResource(id = R.string.ppe),
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth(),
                        fontSize = 20.sp,
                        lineHeight = 25.sp
                    )
                }


                item {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start

                        ) {

                            Text(
                                text = stringResource(id = R.string.maskI)+":",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = maskI,
                                fontSize = 18.sp
                            )


                        }
                        Row(
                            horizontalArrangement = Arrangement.Start

                        ) {

                            Text(
                                text = stringResource(id = R.string.maskS)+":",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = maskS,
                                fontSize = 18.sp
                            )

                        }


                    }


                }

                stickyHeader {
                    Text(
                        text = stringResource(id = R.string.prob) + " [%]",
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxWidth(),
                        fontSize = 20.sp
                    )
                }


                item {
                    Text(
                        text = String.format(Locale.US,"%.1f",prob),
                        fontSize = 18.sp
                    )
                }



            }
        }

    }
}


