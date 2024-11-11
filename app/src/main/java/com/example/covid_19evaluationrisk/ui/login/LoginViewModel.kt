package com.example.covid_19evaluationrisk.ui.login
import android.content.Context
import android.icu.util.ULocale.getDisplayLanguage
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*



val firebase = Firebase.database("https://covid19-evaluation-default-rtdb.europe-west1.firebasedatabase.app/")

class LoginViewModel: ViewModel() {


    private var auth: FirebaseAuth = FirebaseAuth.getInstance()




    val user = auth.currentUser

  //var username by mutableStateOf(user?.displayName)

    var emailUser by mutableStateOf(user?.email)

    private var userIsLoggedIn by mutableStateOf(false)

    var loginSuccess by mutableStateOf(false)
        private set
    var registerSuccess by mutableStateOf(false)
        private set

    var emailSent by mutableStateOf(false)







    fun loginUser(email: String, password: String, context: Context){

        //val navHostController = NavHostController(context)
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(it.isSuccessful){


                        user?.let {
                            //   name = it.displayName
                            emailUser = it.email
                            //   photoUrl = it.photoUrl

                        }


                        //val user: String = auth.currentUser?.email ?: ""


                        loginSuccess = true
                        //      Log.d("TAG",loginSuccess.toString())
                        if (Locale.getDefault().displayLanguage == "italiano") {
                            Toast.makeText(
                                context,
                                "Log in effettuato con successo",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("language", Locale.getDefault().displayLanguage)
                            Toast.makeText(
                                context,
                                "You successfully signed in",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        userIsLoggedIn = true


                        //navHostController.navigate(DashboardDestination.route)
                    } else {
                        try {
                            throw it.exception!!
                        } catch (e : FirebaseAuthInvalidCredentialsException){
                            if (Locale.getDefault().displayLanguage == "italiano") {
                                Toast.makeText(context, "Credenziali non corrette", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG)
                                    .show()

                            }
                        } catch (e2: FirebaseAuthInvalidUserException) {
                            if (Locale.getDefault().displayLanguage == "italiano") {
                                Toast.makeText(
                                    context,
                                    "Non risultano account registrati con questo indirizzo e-mail",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {
                                Toast.makeText(
                                    context,
                                    "No user registered with this e-mail address",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                    }
                }
        } else {
            if (Locale.getDefault().displayLanguage == "italiano") {

                Toast.makeText(context, "Riempi tutti i campi", Toast.LENGTH_LONG ).show()
            } else{
                Toast.makeText(context, "Empty fields", Toast.LENGTH_LONG).show()
            }
        }
        loginSuccess = false
    }


   /* fun setUserName(name: String, context: Context){

        if (name.isNotEmpty()){
            auth.currentUser!!.updateProfile(
                userProfileChangeRequest {
                    displayName = name
                }
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(context,"User", Toast.LENGTH_LONG)
                    }
                }
        }

    }
*/

    fun registerNewUser (email: String, password: String, confirmedPassword: String, context: Context, username: String ){
        // var auth: FirebaseAuth = FirebaseAuth.getInstance()
        // val navHostController = NavHostController(context)
        if (email.isNotEmpty() && password.isNotEmpty() && confirmedPassword.isNotEmpty() && username.isNotEmpty()){
            if (password == confirmedPassword){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                        if(it.isSuccessful){

                            auth.currentUser!!.updateProfile(
                                userProfileChangeRequest {
                                    displayName = username
                                }
                            )
                            registerSuccess = true
                            userIsLoggedIn = true
                                                       //       Log.d("success", registerSuccess.toString())

                            // Toast.makeText(context,loginSuccess.toString(), Toast.LENGTH_LONG).show()

                            if (Locale.getDefault().displayLanguage == "italiano") {
                                Toast.makeText(
                                    context,
                                    "Registrazione avvenuta con successo",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {
                                Toast.makeText(
                                    context,
                                    "You successfully signed up",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                            //navHostController.navigate(ProfileDestination.route)
                        } else {
                            //  Log.d("un-successful", it.exception.toString())
                            try {
                                throw  it.exception!!
                            } catch (e : FirebaseAuthWeakPasswordException){
                                if (Locale.getDefault().displayLanguage == "italiano") {
                                    Locale.setDefault(Locale.ITALY)
                                    Toast.makeText(context, e.reason.toString(), Toast.LENGTH_LONG)
                                        .show()
                                } else {
                                    Toast.makeText(context, e.reason.toString(), Toast.LENGTH_LONG)
                                        .show()

                                }
                            } catch (e2 : FirebaseAuthUserCollisionException){

                                if (Locale.getDefault().displayLanguage == "italiano") {
                                    Toast.makeText(
                                        context,
                                        "E-mail già in uso",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "E-mail already in use",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                            }

                            if (Locale.getDefault().displayLanguage == "italiano") {
                                Toast.makeText(context, "Registrazione fallita!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "Sign Up Failed!", Toast.LENGTH_SHORT)
                                    .show()

                            }
                            //Toast.makeText(context,it.exception.g, Toast.LENGTH_LONG).show()
                            //Toast.makeText(context,)
                            // FirebaseAuthWeakPasswordException.getReason()
                        }
                    }


            } else {
                //  Log.d("password", "Password is not matching")
                if (Locale.getDefault().displayLanguage == "italiano") {
                    Toast.makeText(context, "Le password non corrispondono", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "Passwords are not matching", Toast.LENGTH_LONG).show()
                }
            }
        } else {

            //  Log.d("empty", "Empty fields are not allowed !!")
            if (Locale.getDefault().displayLanguage == "italiano") {
                Toast.makeText(context, "Non sono ammessi campi vuoti !!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Empty fields are not allowed !!", Toast.LENGTH_LONG).show()

            }
        }





        registerSuccess = false
    }

    fun userLogOut(context: Context){
        auth.signOut().also {
            if (Locale.getDefault().displayLanguage == "italiano") {
                Toast.makeText(context, "Disconnesso", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "You successfully logged out", Toast.LENGTH_LONG).show()
            }
            userIsLoggedIn = false
        }

    }





    fun resetPassword(email:String, context:Context){


        if(email.isNotEmpty()){
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (it.isSuccessful){

                        emailSent = true
                        if (Locale.getDefault().displayLanguage == "italiano") {
                            Toast.makeText(context, "E-mail inviata con successo", Toast.LENGTH_LONG)
                        } else {
                            Toast.makeText(context, "E-mail successfully sent", Toast.LENGTH_LONG)
                                .show()

                        }
                        Log.d("forgot", emailSent.toString())
                    } else {
                        if (Locale.getDefault().displayLanguage == "italiano") {
                            Toast.makeText(context, "Invio fallito", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(context, "Failed to sent e-mail", Toast.LENGTH_LONG)
                                .show()

                        }
                        Log.d("forgot", emailSent.toString())
                    }

                }
        } else {
            if (Locale.getDefault().displayLanguage == "italiano") {
                Toast.makeText(context, "Inserisci un indirizzo e-mail valido", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(context, "Enter a valid e-mail", Toast.LENGTH_LONG).show()
            }
        }





    }






}
