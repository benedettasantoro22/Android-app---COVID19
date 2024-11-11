package com.example.covid_19evaluationrisk.firebase
import com.google.firebase.database.IgnoreExtraProperties
@IgnoreExtraProperties
data class Evaluation (
        val id: String? = null,
        val date: String? = null,
        //val user: String? = null,
        val volume: Double = 0.toDouble(),
        val ventilation: Double = 0.toDouble(),
        val infectious: Int = 0,
        val susceptible: Int = 0,
        val time: Double = 0.toDouble(),
        val humidity: Double = 0.00367323,
        val maskI: Double = 0.toDouble(),
        val maskS: Double = 0.toDouble(),
        val activity: Double = 1.521,
        val hepa: Double = 0.toDouble(),
        val probability: Double = 0.toDouble()
        )









