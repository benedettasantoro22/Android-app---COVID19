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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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


object CreateDestination : NavigationDestination {
    override val route = "account"
    override val titleRes = R.string.create_account
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateAccount(navController: NavHostController, viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)) {

    val loginData = LoginData()
    val coroutineScope = rememberCoroutineScope()
    val enabled = true
    val focusRequester = FocusRequester()
    var passwordIsVisible by remember { mutableStateOf(false) }
   // var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(loginData.email) }
    var password by remember { mutableStateOf(loginData.password) }
    var passwordVerification by remember { mutableStateOf(loginData.password) }
    var username by remember { mutableStateOf(loginData.name)   }
    val context = LocalContext.current
    //val progressBar = CircularProgressIndicator( color = MaterialTheme.colors.primary)


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
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
            ) {
                OutlinedTextField(
                    value = email,   //loginData.email,
                    onValueChange = {
                        email = it
                        //loginData.copy(email = it)
                        //    Log.d("tag", email + "   " + loginData.toString())

                    },
                    label = { Text(stringResource(id = R.string.email), color = MaterialTheme.colors.onPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    enabled = enabled,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Mail, contentDescription = "")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colors.surface
                    )

                )


                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
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
                    leadingIcon = {
                        Icon(contentDescription = "", imageVector = Icons.Filled.Lock)
                    },
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

                OutlinedTextField(
                    value = passwordVerification,
                    onValueChange = { passwordVerification = it },
                    label = { Text(stringResource(id = R.string.confirms), color = MaterialTheme.colors.onPrimary) },
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
                    leadingIcon = {
                        Icon(contentDescription = "", imageVector = Icons.Filled.Lock)
                    },

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

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = MaterialTheme.colors.onPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    enabled = enabled,
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colors.surface
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "" )
                    }

                )
            }

        }


        //SignUp(loginData = loginData)
        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.registerNewUser(email, password, passwordVerification, context, username )

                    }

                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface)
            ) {
                Text(
                    text = stringResource(id = R.string.create_account),
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }


        item {
            LaunchedEffect(viewModel.registerSuccess) {
                if (viewModel.registerSuccess) {
                    navController.navigate(HomepageDestination.route)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }



        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.question))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(id = R.string.login),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable { navController.navigate(HomeDestination.route) },
                    color = MaterialTheme.colors.onBackground
                )
            }
        }

    }


}


