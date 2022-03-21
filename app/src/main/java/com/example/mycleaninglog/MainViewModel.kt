package com.example.mycleaninglog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycleaninglog.dto.myRoom
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainViewModel : ViewModel() {
    var myRooms: MutableLiveData<List<myRoom>> = MutableLiveData<List<myRoom>>()
    private lateinit var firestore : FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        listenToMyRooms()
    }

    private fun listenToMyRooms() {
        firestore.collection("myRooms").addSnapshotListener{
            snapshot, e->
                if(e != null){
                    Log.w("Listen faled", e)
                    return@addSnapshotListener
                }
            snapshot?.let {
                val allMyRooms = ArrayList<myRoom>()
                val documents = snapshot.documents
                documents.forEach{
                    var myRoom = it.toObject(myRoom::class.java)
                    myRoom?.let{
                        allMyRooms.add(it)
                    }
                }
                myRooms.value = allMyRooms
            }

        }
    }

    fun save(preConRoom: myRoom) {
        val document = firestore.collection("myRooms").document()

        val handle = document.set(preConRoom)
        handle.addOnSuccessListener{Log.d("Firebase", "Document Saved")}
        handle.addOnFailureListener{Log.e("Firebase", "Save Failed $it")}
    }


}