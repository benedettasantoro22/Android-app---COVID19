package com.example.covid_19evaluationrisk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.covid_19evaluationrisk.firebase.Evaluation
import com.example.covid_19evaluationrisk.ui.login.firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.exp


class EvaluationViewModel: ViewModel() {


    private val user = Firebase.auth.currentUser?.displayName.toString()

    private val _uiState = MutableStateFlow(EvaluationD())
    val uiState: StateFlow<EvaluationD> = _uiState.asStateFlow()




   /* fun setUsername(username: String){

        _uiState.update {
            it.copy(
                user = username
            )
        }

    }
*/

    fun setDate(date: String){

        _uiState.update {
            it.copy(
                date = date )
        }


    }


    fun setRoomVolume(volume: String ){

        _uiState.update {
            it.copy(
                volume = volume,
            )
        }
        Log.d("uiState", _uiState.value.volume)

    }

    fun setRoomVentilation(ventilation: String){


        _uiState.update {
            it.copy(ventilation = ventilation)
        }
        Log.d("uiState", _uiState.value.ventilation)

        /*_uiState.update {
          //  it.copy
            (
               // volume = volume,
                ventilation = ventilation
            )
        }*/

    }

    fun setHumidity(humidityLevel: String){



        when (humidityLevel) {
            "21" -> {
                _uiState.update {
                    it.copy(
                        relativeHumidity = 0.00367323.toString()
                    )
                }
                Log.d("uiState", _uiState.value.relativeHumidity)
            }

            "40" -> {
                _uiState.update {
                    it.copy(
                        relativeHumidity = 0.15772872.toString()
                    )
                }
                Log.d("uiState", _uiState.value.relativeHumidity)
            }
            else -> {
                _uiState.update {
                    it.copy(
                        relativeHumidity = 0.4016.toString()
                    )
                }
                Log.d("uiState", _uiState.value.relativeHumidity)
            }

        }

    }



    fun setIOccupants(infectious: String) {

        _uiState.update {
            it.copy(
                infectious = infectious,
            )
        }
        Log.d("uiState", _uiState.value.infectious)
    }



     fun setIMask(maskI: String){


        when(maskI){
            "no mask"    -> {
                _uiState.update {
                    it.copy(
                        maskI = 0.toString()
                    )
                }
                //Log.d("uiState",  _uiState.value.maskI)
            }

            "nessuna"-> {
                _uiState.update {
                    it.copy(
                        maskI = 0.toString()
                    )
                }
                //Log.d("uiState",  _uiState.value.maskI)
            }

            "surgical mask"  -> {
                _uiState.update {
                    it.copy(
                        maskI = 0.53.toString()
                    )
                }
                //Log.d("uiState",  _uiState.value.maskI)
            }
            "masc. chirurgica"  -> {
                _uiState.update {
                    it.copy(
                        maskI = 0.53.toString()
                    )
                }
                //Log.d("uiState",  _uiState.value.maskI)
            }


            "FFP2 respirator" -> {
                _uiState.update {
                    it.copy(
                        maskI = 0.9.toString()
                    )
                }
                //Log.d("uiState",  _uiState.value.maskI)
            }

            "masc. FFP2" -> {
                _uiState.update {
                    it.copy(
                        maskI = 0.9.toString()
                    )
                }
                //Log.d("uiState",  _uiState.value.maskI)
            }


        }




    }




    fun setSOccupants(susceptible: String) {

        _uiState.update {
            it.copy(
                susceptible = susceptible,
            )
        }
        Log.d("uiState", _uiState.value.susceptible)

    }

    fun setSMask( maskS: String){

        when(maskS){
            "no mask" -> {
                _uiState.update {
                    it.copy(
                        maskS = 0.toString()
                    )
                }
                Log.d("uiState",  _uiState.value.maskS)
            }
            "nessuna" -> {
                _uiState.update {
                    it.copy(
                        maskS = 0.toString()
                    )
                }
                Log.d("uiState",  _uiState.value.maskS)
            }
            "surgical mask" -> {
                _uiState.update {
                    it.copy(
                        maskS = 0.49.toString()
                    )
                }
                Log.d("uiState",  _uiState.value.maskS)
            }

            "FFP2 respirator" -> {
                _uiState.update {
                    it.copy(
                        maskS = 0.9.toString()
                    )
                }
              //  Log.d("uiState",  _uiState.value.maskS)
            }
            "masc. FFP2" -> {
                _uiState.update {
                    it.copy(
                        maskS = 0.9.toString()
                    )
                }
                //  Log.d("uiState",  _uiState.value.maskS)
            }

        }




    }



    fun setTime(time: String){
        _uiState.update {
            it.copy(
                time = time
            )
        }
        Log.d("uiState",_uiState.value.time)

        Log.d("uiState", _uiState.value.toString())
    }



    fun setActivity(activity: String){



        when(activity){

            "breathing" -> {
                _uiState.update {
                    it.copy(activity = 1.521.toString())
                }

            }


            "respirare" -> {
                _uiState.update {
                    it.copy(activity = 1.521.toString())
                }

            }
            "speaking"  -> {
                _uiState.update {
                    it.copy(activity =  44.46.toString())
                }

            }
            "parlare"  -> {
                _uiState.update {
                    it.copy(activity =  44.46.toString())
                }

            }

            "singing"  -> {
                _uiState.update {
                    it.copy(activity = 105.3.toString())
                }
            }

            "cantare"  -> {
                _uiState.update {
                    it.copy(activity = 105.3.toString())
                }
            }


        }
        Log.d("activity", _uiState.value.activity)




    }

    fun setHEPA(check:Boolean){



        when(check) {

            true -> {
                _uiState.update {
                    it.copy(hepa = 0.9997.toString())
                }
            }

            false -> {
                _uiState.update {
                    it.copy(hepa = 0.toString())

                }

            }
        }
    }


    fun computeProbability(){

        val p0 =  exp(
            (1-_uiState.value.maskI.toDouble())
                .times(1 - _uiState.value.maskS.toDouble())
                .times(_uiState.value.infectious.toDouble())
                .times(_uiState.value.activity.toDouble())
                .times(_uiState.value.time.toDouble())
                .times(0.4824)
                .times(-1)
                .div(_uiState.value.volume.toDouble().times(0.24+_uiState.value.relativeHumidity.toDouble() + _uiState.value.ventilation.toDouble().times(1 + _uiState.value.hepa.toDouble()))+ 7.26)
        )

        val p = 1 -p0

        Log.d("p0", p0.toString())
        Log.d("p",p.toString())
        _uiState.update {
            it.copy(
                infectionProbability = p.toString()
            )
        }
        Log.d("probability", _uiState.value.infectionProbability)
        Log.d("uiState", uiState.value.toString())



    }


    fun saveEvaluation(evaluationDetails: EvaluationD){

        //Log.d("insert",evaluationDetails.toString())
       // covidRepository.insert(covidEvaluation = evaluationDetails.toCovidEvaluation())
        firebase.getReference("users/"+ user +"/Evaluations").push().setValue(evaluationDetails.toEvaluation())




        }

}






/*


data class EvaluationDetails(
    val id: Int = 0,
    val date: String = "",
    val user: String = "",
    val volume: String = "",
    val ventilation: String = "",
    val infectious: String = "",
    val susceptible: String = "",
    val time: String = "",
    val relativeHumidity: String = "0.00367323",
    val activity: String = "1.521",
    val hepa: String = "0",
    val maskI: String = "0",
    val maskS: String = "0",
    val infectionProbability: String = ""
)
*/
data class EvaluationD(
    val id: String = "",
    val date: String = "",
  //  val user: String = "",
    val volume: String = "",
    val ventilation: String = "",
    val infectious: String = "",
    val susceptible: String = "",
    val time: String = "",
    val relativeHumidity: String = "0.00367323",
    val activity: String = "1.521",
    val hepa: String = "0",
    val maskI: String = "0",
    val maskS: String = "0",
    val infectionProbability: String = ""



)




/*

fun EvaluationDetails.toCovidEvaluation(): CovidEvaluation = CovidEvaluation(
    id = id,
    date = date,
    user = user,
    volume = (volume.toDoubleOrNull() ?: 0.toDouble()) ,
    ventilation = (ventilation.toDoubleOrNull() ?: 0.toDouble()) ,
    infectious = (infectious.toIntOrNull() ?: 0),
    susceptible = (susceptible.toIntOrNull() ?: 0),
    time = (time.toDoubleOrNull()  ?: 0.toDouble()) ,
    humidity = (relativeHumidity.toDoubleOrNull() ?: 0.toDouble()) ,
    maskI = (maskI.toDoubleOrNull() ?: 0.toDouble()) ,
    maskS = (maskS.toDoubleOrNull() ?: 0.toDouble()) ,
    activity = (activity.toDoubleOrNull() ?: 0.toDouble()),
    hepa = (hepa.toDoubleOrNull() ?: 0.toDouble()),
    probability = (infectionProbability.toDoubleOrNull() ?: 0.toDouble())

)


fun CovidEvaluation.toEvaluationUiState( isEntryValid: Boolean = false): EvaluationUiState = EvaluationUiState(
    evaluationDetails = this.toEvaluationDetails(),
    isEntryValid = isEntryValid
)


fun EvaluationDetails.toEvaluation(): Evaluation = Evaluation(
    id = id.toString(),
    date = date,
//    user = user,
    volume = (volume.toDoubleOrNull() ?: 0.toDouble()) ,
    ventilation = (ventilation.toDoubleOrNull() ?: 0.toDouble()) ,
    infectious = (infectious.toIntOrNull() ?: 0),
    susceptible = (susceptible.toIntOrNull() ?: 0),
    time = (time.toDoubleOrNull()  ?: 0.toDouble()) ,
    humidity = (relativeHumidity.toDoubleOrNull() ?: 0.toDouble()) ,
    maskI = (maskI.toDoubleOrNull() ?: 0.toDouble()) ,
    maskS = (maskS.toDoubleOrNull() ?: 0.toDouble()) ,
    activity = (activity.toDoubleOrNull() ?: 0.toDouble()),
    hepa = (hepa.toDoubleOrNull() ?: 0.toDouble()),
    probability = (infectionProbability.toDoubleOrNull() ?: 0.toDouble())

)




*/

fun Evaluation.toEvaluationD(): EvaluationD = EvaluationD(
    id = id!!,
    //user = user,
    date = date!!,
    volume = volume.toString(),
    ventilation = ventilation.toString(),
    infectious = infectious.toString(),
    susceptible = susceptible.toString(),
    time = time.toString(),
    relativeHumidity = humidity.toString(),
    activity = activity.toString(),
    hepa = hepa.toString(),
    maskI = maskI.toString(),
    maskS = maskS.toString(),
    infectionProbability = probability.toString()
)





fun EvaluationD.toEvaluation(): Evaluation = Evaluation(
    id = id,
    //user = user,
    date = date,
    volume = volume.toDouble(),
    ventilation = ventilation.toDouble(),
    infectious = infectious.toInt(),
    susceptible = susceptible.toInt(),
    time = time.toDouble(),
    humidity = relativeHumidity.toDouble(),
    activity = activity.toDouble(),
    hepa = hepa.toDouble(),
    maskI = maskI.toDouble(),
    maskS = maskS.toDouble(),
    probability = infectionProbability.toDouble()
)





