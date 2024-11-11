package com.example.covid_19evaluationrisk.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid_19evaluationrisk.R
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel
import com.example.covid_19evaluationrisk.ui.navigation.NavigationDestination
import com.example.covid_19evaluationrisk.ui.viewmodel.AppViewModelProvider
import kotlinx.coroutines.launch


data class LoginData(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val confirmedPassword: String = ""
)


object HomeDestination: NavigationDestination{
    override val route = "welcome"
    override val titleRes = R.string.welcome
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(navHostController: NavHostController,modifier: Modifier = Modifier, viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory) ) {

    val coroutineScope = rememberCoroutineScope()
    val loginData = LoginData()
    val enabled = true

    val focusRequester = FocusRequester()
    var passwordIsVisible by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf(loginData.email) }
    var password by remember { mutableStateOf(loginData.password) }
    val context = LocalContext.current


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 25.dp, bottom = 10.dp)
    ) {


        item {

            Text(
                text = stringResource(id = R.string.welcome),
                color = MaterialTheme.colors.onPrimary,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Image(
                painter = painterResource(R.drawable.sars_cov_2_without_background),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(0.25f)
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Column(
                modifier = modifier
                    .fillMaxWidth(0.8F),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(id = R.string.email), color = MaterialTheme.colors.onPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
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


                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it

                                    },
                    label = { Text(stringResource(id = R.string.password), color = MaterialTheme.colors.onPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    enabled = enabled,
                    singleLine = true,
                    visualTransformation = if (passwordIsVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        val icon = if (passwordIsVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }
                        IconButton(
                            onClick = {
                                passwordIsVisible = !passwordIsVisible
                            }
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null
                            )
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colors.surface
                    )
                )

                Text(
                    text = stringResource(id = R.string.forgot_password),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable { navHostController.navigate(ForgotDestination.route) }
                        .align(alignment = Alignment.End),
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.loginUser(email, password, context)
                        }

                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = MaterialTheme.typography.button,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                }

                LaunchedEffect(viewModel.loginSuccess) {
                    if (viewModel.loginSuccess) {
                        navHostController.navigate(HomepageDestination.route)
                    }
                }
                Registration(goToRegister = { navHostController.navigate(CreateDestination.route) })

                Image(painter = painterResource(R.drawable.units_sigillo), contentDescription = "")


            }
        }


    }
}


@Composable
fun Registration(goToRegister: () -> Unit ){

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally


        ) {

            Text(text="or",color = MaterialTheme.colors.onSurface)

            Spacer(modifier = Modifier.height(15.dp))
            Button(onClick = goToRegister ,  colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)) {
                Text(
                    text = stringResource(id = R.string.create),
                    style = MaterialTheme.typography.button,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface)
            }


        }


}





