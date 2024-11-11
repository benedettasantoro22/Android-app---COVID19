package com.example.covid_19evaluationrisk.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Spacer as Spacer


object ForgotDestination : NavigationDestination {
    override val route = "forgot"
    override val titleRes = R.string.forgot_password

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForgotPassword(navHostController: NavHostController, viewModel: LoginViewModel = viewModel(factory= AppViewModelProvider.Factory)) {

        val coroutineScope = rememberCoroutineScope()
        val loginData = LoginData()
        val enabled = true

        val focusRequester = FocusRequester()

        var email by remember { mutableStateOf(loginData.email) }
        val context = LocalContext.current


        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 10.dp)
        ) {

            item {
                Image(
                    painter = painterResource(R.drawable.sars_cov_2_without_background),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(0.3f)
                )
            }


            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {


                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8F),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Enter your e-mail address",
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(id = R.string.email), color = MaterialTheme.colors.onPrimary) },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester),
                        enabled = enabled,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colors.surface
                        )

                    )

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.resetPassword(email, context)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),
                        modifier = Modifier.align(Alignment.End)

                        ) {
                        Text(
                            text = "Submit",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }

            item {

                    LaunchedEffect(viewModel.emailSent) {
                        if (viewModel.emailSent) {
                            navHostController.navigate(HomeDestination.route)
                        }
                    }

                }
            }



}
