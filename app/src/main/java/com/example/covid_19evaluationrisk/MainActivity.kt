package com.example.covid_19evaluationrisk
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.covid_19evaluationrisk.ui.theme.Covid19EvaluationRiskTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Covid19EvaluationRiskTheme {
                // A surface container using the 'background' color from the theme
                    CovidApp()

            }
        }
    }
}
