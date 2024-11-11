package com.example.covid_19evaluationrisk.firebase

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid_19evaluationrisk.ui.login.firebase
import com.example.covid_19evaluationrisk.ui.screens.ItemDetailsDestination
import com.example.covid_19evaluationrisk.ui.viewmodel.EvaluationD
import com.example.covid_19evaluationrisk.ui.viewmodel.toEvaluationD
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailFirebaseViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val itemKey: String = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])
    private val user = Firebase.auth.currentUser?.displayName.toString()


   // val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    val eval : StateFlow<EvaluationDetailsUiState> =


        firebase.getReference("users/"+user+"/Evaluations").child(itemKey)
            .snapshots.map {
                EvaluationDetailsUiState(it.getValue(Evaluation::class.java)!!.toEvaluationD())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS_Det),
                initialValue = EvaluationDetailsUiState()
            )



    companion object {
        internal const val TIMEOUT_MILLIS_Det = 5_000L
    }


           /*     .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            eval = snapshot.getValue(Evaluation::class.java)!!
                            }
                        }
                    override fun onCancelled(error: DatabaseError) {
                        response.value = DataState.Failure(error.message)
                    }
                })





*/


   /* private fun getEval(key: String) : Evaluation{

        var eval = Evaluation()
        firebase.getReference("users/"+user+"/Evaluations").child(key)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        eval = snapshot.getValue(Evaluation::class.java)!!


                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })


        return eval

    }*/



}

data class EvaluationDetailsUiState(
    val evaluationDetails: EvaluationD = EvaluationD()
)

