package com.example.covid_19evaluationrisk.ui.viewmodel

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.covid_19evaluationrisk.firebase.DetailFirebaseViewModel
import com.example.covid_19evaluationrisk.firebase.FirebaseViewModel
import com.example.covid_19evaluationrisk.ui.login.LoginViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            LoginViewModel()
        }
        initializer {
            EvaluationViewModel()
        }
        initializer{
            FirebaseViewModel()
        }
        initializer{
            DetailFirebaseViewModel(this.createSavedStateHandle())
        }


    }
}
/*
fun CreationExtras.covidApplication(): CovidApplication =
    (this[APPLICATION_KEY] as CovidApplication)
*/