package com.example.covid_19evaluationrisk.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid_19evaluationrisk.CovidTopAppBar
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination


object OccupantDestination: NavigationDestination{
    override val route = "occupants"
    override val titleRes = R.string.occupants
}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OccupantsScreen(
    navigateForward: () -> Unit,
    navigateBack: () -> Unit,
    onInfectiousChange: (String) -> Unit = {},
    onMaskIChange: (String) -> Unit = {},
    onSetActivity: (String) -> Unit = {},
    onSusceptibleChange: (String) -> Unit = {},
    onMaskSChange: (String) -> Unit = {},
    onTimeChange: (String) -> Unit = {}

){

    val focusRequester = FocusRequester()
    val enabled = true
    var expandedI by remember {  mutableStateOf(false) }
    var expandedS by remember {  mutableStateOf(false) }
    var expandActivity by remember{ mutableStateOf(false) }
    var infectious by rememberSaveable { mutableStateOf("") }
    var susceptible by rememberSaveable { mutableStateOf("") }
    val masks = arrayListOf(stringResource(id = R.string.no), stringResource(id = R.string.surgical),
        stringResource(id = R.string.ffp2))
    val activities = arrayListOf(stringResource(id = R.string.breath), stringResource(id = R.string.speak),
        stringResource(id = R.string.sing))
    var selectedItemI by remember{ mutableStateOf(masks[0]) }
    var selectedItemS by remember{ mutableStateOf(masks[0]) }
    var selectedActivity by remember { mutableStateOf(activities[0]) }
    var time by rememberSaveable { mutableStateOf("") }
    // remember the selected item

    var showAlert by remember { mutableStateOf(false) }
    if (showAlert){
        ReadConfirmationDialog(
            onReadConfirm = { showAlert = false }, message = stringResource(id = R.string.please), title = "")
    }

    Scaffold(
        topBar = {
            CovidTopAppBar(
                title = stringResource(id = OccupantDestination.titleRes),
                canNavigateBack = true,
                navigateUp =  navigateBack
            )
        }


    ){

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 10.dp)


        ) {


            item{
                Image(painter = painterResource(R.drawable.sars_cov_2_without_background), contentDescription = "", modifier = Modifier.fillMaxWidth(0.25f))
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }


            item {
                Text(text= stringResource(id = R.string.information), color = MaterialTheme.colors.onPrimary, textAlign = TextAlign.Start, fontSize = 20.sp)

            }

            item { Spacer(modifier = Modifier.height(15.dp)) }


            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8F),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {


                    OutlinedTextField(
                        value = infectious,
                        onValueChange = {
                            infectious = it
                            onInfectiousChange(it)
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.i),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onPrimary
                            )
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        singleLine = true,
                        enabled = enabled,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colors.surface
                        )
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedI,
                        onExpandedChange = {
                            expandedI = !expandedI
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                    ) {
                        // text field
                        TextField(
                            value = selectedItemI,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.maskI),
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.onPrimary,
                                    fontSize = 16.sp
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedI
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )

                        // menu
                        DropdownMenu(
                            expanded = expandedI,
                            onDismissRequest = { expandedI = false },
                            modifier = Modifier
                                .exposedDropdownSize()
                        ) {
                            // this is a column scope
                            // all the items are added vertically
                            masks.forEach { selectedOption ->
                                // menu item
                                DropdownMenuItem(
                                    onClick = {
                                        selectedItemI = selectedOption
                                        onMaskIChange(selectedOption)
                                        expandedI = false
                                    }) {
                                    Text(
                                        text = selectedOption,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colors.onPrimary,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }


                    ExposedDropdownMenuBox(
                        expanded = expandActivity,
                        onExpandedChange = {
                            expandActivity = !expandActivity
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                    ) {
                        // text field
                        TextField(
                            value = selectedActivity,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.activity),
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.onPrimary,
                                    fontSize = 16.sp
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedI
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )

                        // menu
                        DropdownMenu(
                            expanded = expandActivity,
                            onDismissRequest = { expandActivity = false },
                            modifier = Modifier
                                .exposedDropdownSize()
                        ) {
                            // this is a column scope
                            // all the items are added vertically
                            activities.forEach { selectedOption ->
                                // menu item
                                DropdownMenuItem(
                                    onClick = {
                                        selectedActivity = selectedOption
                                        onSetActivity(selectedOption)
                                        expandActivity = false
                                    }) {
                                    Text(
                                        text = selectedOption,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colors.onPrimary,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = susceptible,
                        onValueChange = {
                            susceptible = it
                            onSusceptibleChange(it)
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.s),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onPrimary
                            )
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        singleLine = true,
                        enabled = enabled,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colors.surface
                        )
                    )


                    ExposedDropdownMenuBox(
                        expanded = expandedS,
                        onExpandedChange = {
                            expandedS = !expandedS
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                    ) {
                        // text field
                        TextField(
                            value = selectedItemS,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.maskS),
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.onPrimary,
                                    fontSize = 16.sp
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedS
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )

                        // menu
                        DropdownMenu(
                            expanded = expandedS,
                            onDismissRequest = { expandedS = false },
                            modifier = Modifier
                                .exposedDropdownSize()
                        ) {
                            // this is a column scope
                            // all the items are added vertically
                            masks.forEach { selectedOption ->
                                // menu item
                                DropdownMenuItem(
                                    onClick = {
                                        selectedItemS = selectedOption
                                        onMaskSChange(selectedOption)
                                        expandedS = false
                                    }) {
                                    Text(
                                        text = selectedOption,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colors.onPrimary,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = time,
                        onValueChange = {
                            time = it
                            onTimeChange(it)
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.time),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onPrimary
                            )
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        singleLine = true,
                        enabled = enabled,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colors.surface
                        )
                    )


                }
            }

            item{ Spacer(modifier = Modifier.height(15.dp)) }

            item {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {




                    Button(onClick = navigateBack, colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)) {


                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "")

                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = stringResource(R.string.back_page),
                            style = MaterialTheme.typography.button,
                            fontSize = 16.sp,
                            color = MaterialTheme.colors.onSurface
                        )



                    }

                    Button(
                        onClick = {
                            if (infectious.isNotEmpty() && susceptible.isNotEmpty() && time.isNotEmpty()) {
                                navigateForward()
                            } else {
                                showAlert = true
                            }
                        } ,


                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)) {
                        Text(
                            text = stringResource(id = R.string.next_page),
                            style = MaterialTheme.typography.button,
                            fontSize = 16.sp,
                            color = MaterialTheme.colors.onSurface
                        )

                        Spacer(modifier = Modifier.width(15.dp))

                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "")
                    }




                }



            }










        }

    }


}