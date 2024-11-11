package com.example.covid_19evaluationrisk.firebase


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.covid_19evaluationrisk.ui.login.firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
class FirebaseViewModel : ViewModel(){

    private val user = Firebase.auth.currentUser!!.displayName.toString()


    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList = mutableListOf<Evaluation>()
        val keyList = mutableListOf<String>()
        response.value = DataState.Loading
        firebase.getReference("users/"+user+"/Evaluations")
            .orderByChild("date")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                       // val key = DataSnap.key
                        val foodItem = DataSnap.getValue(Evaluation::class.java)
                        val foodKey = DataSnap.key!!
                        firebase.getReference("users/"+user+"/Evaluations").child(foodKey).child("id").setValue(foodKey)



                       // foodItem!!.copy(user = key )
                        if (foodItem != null) {
                            tempList.add(foodItem)
                            keyList.add(foodKey)
                        }
                    }
                    response.value = DataState.Success(tempList,keyList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = DataState.Failure(error.message)
                }

            })


    }


}

sealed class DataState {
    class Success(val data: MutableList<Evaluation>, val keys: MutableList<String>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}