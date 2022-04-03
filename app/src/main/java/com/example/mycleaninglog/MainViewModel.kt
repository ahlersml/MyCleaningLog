package com.example.mycleaninglog

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.dto.User
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainViewModel : ViewModel() {
    var myRooms: MutableLiveData<List<myRoom>> = MutableLiveData<List<myRoom>>()
    var cleaningTasks: MutableLiveData<List<cleaningTask>> = MutableLiveData<List<cleaningTask>>()
    var selectedRoom : myRoom? = null
    private lateinit var firestore : FirebaseFirestore
    var user : User? = null
    var users: MutableLiveData<List<User>> = MutableLiveData<List<User>>()



    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        listenToMyRooms()

    }

    fun listenToMyRooms() {
        user?.let {
            user ->
            firestore.collection("users").document(user.uid).collection("myRooms").addSnapshotListener{
                snapshot, e->
                    if(e != null){
                        Log.w("Listen failed", e)
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
    }
    internal fun listenToCleaningTasks() {
        selectedRoom?.let{
            selectedRoom->

        firestore.collection("myRooms").document(selectedRoom.uniqueID).collection("cleaningTasks").addSnapshotListener{
                snapshot, e->
            if(e != null){
                Log.w("Listen failed", e)
                return@addSnapshotListener
            }
            snapshot?.let {
                val allMyTasks = ArrayList<cleaningTask>()
                val documents = snapshot.documents
                documents.forEach{
                    var myTask = it.toObject(cleaningTask::class.java)
                    myTask?.let{
                        allMyTasks.add(it)
                    }
                }
                cleaningTasks.value = allMyTasks
            }

        }
        }
    }

    fun saveRoom(preConRoom: myRoom) {
        user?.let{
            user ->
            val document = if(preConRoom.uniqueID == null || preConRoom.uniqueID.isEmpty()) {
                firestore.collection("users").document(user.uid).collection("myRooms").document()
            }else{
                firestore.collection("users").document(user.uid).collection("myRooms").document(preConRoom.uniqueID)
            }
            preConRoom.uniqueID = document.id
            val handle = document.set(preConRoom)
            handle.addOnSuccessListener{Log.d("Firebase", "Document Saved")}
            handle.addOnFailureListener{Log.e("Firebase", "Save Failed $it")}
        }
    }

    fun deleteRoom(preConRoom: myRoom) {
        user?.let {
            user->
        val document = firestore.collection("users").document(user.uid).collection("myRooms").document(preConRoom.uniqueID)
        document.delete()
        }
    }

    fun saveCleaningTask(preConTask: cleaningTask, selectedRoom: myRoom){
        val document = if(preConTask.uniqueID == null || preConTask.uniqueID.isEmpty()){
            firestore.collection("myRooms").document(selectedRoom.uniqueID).collection("cleaningTasks").document()
        } else {
            firestore.collection("myRooms").document(selectedRoom.uniqueID).collection("cleaningTasks").document(preConTask.uniqueID)
        }
        preConTask.uniqueID = document.id
        val handle = document.set(preConTask)
        handle.addOnSuccessListener{Log.d("Firebase", "DocumentSaved")}
        handle.addOnFailureListener{Log.e("Firebase", "Save Failed $it")}
    }

    fun deleteCleaningTask(preConTask: cleaningTask, selectedRoom: myRoom){
        val document = firestore.collection("users").document(user.uid).collection("cleaningTasks").document(preConRoom.uniqueID)
        document.delete()
    }

    fun saveUser() {
        user?.let {
            user->
            val handle = firestore.collection("users").document(user.uid).set(user)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it ") }
        }

    }

}