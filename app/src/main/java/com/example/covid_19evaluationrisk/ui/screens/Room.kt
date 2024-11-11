package com.example.covid_19evaluationrisk.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.covid_19evaluationrisk.CovidTopAppBar
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination


object RoomDestination: NavigationDestination {
    override val route = "room"
    override val titleRes = R.string.room
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoomScreen(
    modifier: Modifier = Modifier,
    navigateForward: () -> Unit,
    navigateUp: () -> Unit,
    //evaluationViewModel: EvaluationViewModel = viewModel(factory= AppViewModelProvider.Factory),
    onVolumeChange: (String) -> Unit = {},
    onVentilationChange: (String) -> Unit = {},
    onHumidityChange: (String) -> Unit = {},
    onCheckChange: (Boolean) -> Unit = {},
    //onSaveClick: () -> Unit,

) {

    val superscript = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 8.sp,
        fontWeight = FontWeight.SemiBold
    )

    val focusRequester = FocusRequester()
    val enabled = true
    var expanded by remember { mutableStateOf(false) }
    val humidity = arrayOf("21", "40", "70")
    // remember the selected item
    var selectedItem by remember { mutableStateOf(humidity[0]) }
    var volume by rememberSaveable { mutableStateOf("") }
    var ventilation by rememberSaveable { mutableStateOf("") }
    var checked by rememberSaveable { mutableStateOf(false)  }
    //val coroutineScope = rememberCoroutineScope()


    var showDialog by remember { mutableStateOf(false) }

    if (showDialog){
        ReadConfirmationDialog(
            onReadConfirm = { showDialog = false }, message = stringResource(id = R.string.message_hepa), title = stringResource(
                id = R.string.filter
            ))
    }


    var showAlert by remember { mutableStateOf(false) }
    if (showAlert){
        ReadConfirmationDialog(
            onReadConfirm = { showAlert = false }, message = stringResource(id = R.string.please), title = "")
    }


    Scaffold(
        topBar = {
            CovidTopAppBar(
                title = stringResource(id = RoomDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }


    ) {

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 10.dp)


        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.sars_cov_2_without_background),
                    contentDescription = "",
                    modifier = modifier.fillMaxWidth(0.25f)
                )
            }

            item { Spacer(modifier = modifier.height(30.dp)) }


            item {
                Text(
                    text = stringResource(id = R.string.information),
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )

            }

            item { Spacer(modifier = Modifier.height(15.dp)) }


            item {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 25.dp, bottom = 10.dp)


                ) {


                    OutlinedTextField(
                        value = volume,
                        onValueChange = {
                            volume = it
                            onVolumeChange(it)
                        },
                        label = {
                            Text(
                                text = buildAnnotatedString {

                                    append(
                                        stringResource(id = R.string.volume) + " [m")
                                    withStyle(superscript) {
                                        append("3")
                                    }
                                    append("]")
                                },
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onPrimary
                            )
                        },
                        singleLine = true,
                        modifier = modifier
                            .focusRequester(focusRequester),
                        enabled = enabled,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colors.surface
                        )
                    )

                    OutlinedTextField(
                        value = ventilation,
                        onValueChange = {
                            ventilation = it
                            onVentilationChange(it)
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.ventilation),
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



                    Spacer(modifier = Modifier.height(10.dp))


                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                    ) {
                        // text field
                        TextField(
                            value = selectedItem,
                            onValueChange = {
                                selectedItem = it

                            },
                            readOnly = true,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.humidity),
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.onPrimary,
                                    fontSize = 16.sp
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.WaterDrop,
                                    contentDescription = "",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )

                        // menu
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .exposedDropdownSize()
                        ) {
                            // this is a column scope
                            // all the items are added vertically
                            humidity.forEach { selectedOption ->
                                // menu item
                                DropdownMenuItem(
                                    onClick = {
                                        selectedItem = selectedOption
                                        onHumidityChange(selectedOption)
                                        expanded = false
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
                }
            }
            //    InputForm( )

            item {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                            onCheckChange(it)
                        },
                        enabled = true,
                        colors = CheckboxDefaults.colors(MaterialTheme.colors.surface)

                    )

                    Text(
                        text = stringResource(id = R.string.filter),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 16.sp
                    )

                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.clickable {
                            showDialog = true
                        }
                    )
                }

            }


            item {
                Button(
                    onClick = {

                        if (volume.isNotEmpty() && ventilation.isNotEmpty()) {
                            navigateForward()
                        } else {

                                showAlert = true
                            }

                        //Toast.makeText( LocalContext.current, stringResource(id = R.string.please),Toast.LENGTH_LONG ).show()



                              },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),
                    enabled = true//evaluationUiState.isEntryValid
                ) {
                    Text(
                        text = stringResource(id = R.string.next_page),
                        style = MaterialTheme.typography.button,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSurface
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }


        }
    }
}






@Composable
fun ReadConfirmationDialog(
    onReadConfirm: () -> Unit,
    title: String,
    message: String,
    //onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(title) },
        text = { Text(message) },
        modifier = modifier.padding(16.dp),
        dismissButton = {    },
        confirmButton = {
            TextButton(onClick = onReadConfirm) {
                Text(text = "Ok")
            }
        }
    )
}
