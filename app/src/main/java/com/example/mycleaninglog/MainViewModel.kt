package com.example.mycleaninglog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.dto.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

/**
 * Class contains methods to allow users to add and delete tasks/rooms and reflect in the firebase.
 */
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

    /**
     * Gathers room information from the firebase.
     */
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

    /**
     * Gathers cleaning task infromation from the firebase
     */
    internal fun listenToCleaningTasks() {

        user?.let { user ->
            selectedRoom?.let { selectedRoom ->

                firestore.collection("users").document(user.uid).collection("myRooms")
                    .document(selectedRoom.uniqueID).collection("cleaningTasks")
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            Log.w("Listen failed", e)
                            return@addSnapshotListener
                        }
                        snapshot?.let {
                            val allMyTasks = ArrayList<cleaningTask>()
                            val documents = snapshot.documents
                            documents.forEach {
                                var myTask = it.toObject(cleaningTask::class.java)
                                myTask?.let {
                                    allMyTasks.add(it)
                                }
                            }
                            cleaningTasks.value = allMyTasks
                        }

                    }
            }
        }
    }

    /**
     * Creates and saves a room to the firebase.
     */
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

    /**
     * Deletes a room from the firebase.
     */
    fun deleteRoom(preConRoom: myRoom) {
        user?.let {
            user->
        val document = firestore.collection("users").document(user.uid).collection("myRooms").document(preConRoom.uniqueID)
        document.delete()
        }
    }

    /**
     * Creates and saves a room to the firebase.
     */
    fun saveTask(preConTask: cleaningTask, selectedRoom: myRoom) {
        user?.let {
                user ->
            val document = if (preConTask.uniqueID == null || preConTask.uniqueID.isEmpty()) {
                firestore.collection("users").document(user.uid).collection("myRooms").document(selectedRoom.uniqueID)
                    .collection("cleaningTasks").document()
            } else {
                firestore.collection("users").document(user.uid).collection("myRooms").document(selectedRoom.uniqueID)
                    .collection("cleaningTasks").document(preConTask.uniqueID)
            }
            preConTask.uniqueID = document.id
            val handle = document.set(preConTask)
            handle.addOnSuccessListener { Log.d("Firebase", "DocumentSaved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save Failed $it") }
        }
    }

    /**
     * Creates and saves a user to the firebase.
     */
    fun saveUser() {
        user?.let {
            user->
            val handle = firestore.collection("users").document(user.uid).set(user)
            handle.addOnSuccessListener { Log.d("Firebase", "Document Saved") }
            handle.addOnFailureListener { Log.e("Firebase", "Save failed $it ") }
        }

    }

    /**
     * Creates and saves a room to the firebase and loads them with prebuilt tasks.
     */
    fun saveItem(roomName: String, roomID: String,viewModel: MainViewModel ){
        var preConRoom = myRoom().apply{
            myRoomName = roomName
            myRoomID = roomID
        }
        viewModel.saveRoom(preConRoom)
        if(preConRoom.myRoomName == "Bedroom"){
            saveCleaningTask("Vacuum", "VAC", viewModel, preConRoom)
            saveCleaningTask("Dusting", "DUS", viewModel, preConRoom)
            saveCleaningTask("Wash Bedding", "WAS", viewModel, preConRoom)
            saveCleaningTask("Laundry", "LAU", viewModel, preConRoom)
        }
        if(preConRoom.myRoomName == "Bathroom"){
            saveCleaningTask("Dusting", "DUS", viewModel, preConRoom)
            saveCleaningTask("Scrub Floors", "FLO", viewModel, preConRoom)
            saveCleaningTask("Scrub Vanity", "VAN", viewModel, preConRoom)
            saveCleaningTask("Scrub Shower", "SHO", viewModel, preConRoom)
            saveCleaningTask("Scrub Bath Tub", "TUB", viewModel, preConRoom)
            saveCleaningTask("Scrub Toilet", "TOI", viewModel, preConRoom)
            saveCleaningTask("Wipe Down Mirror", "MIR", viewModel, preConRoom)
        }
        if(preConRoom.myRoomName == "Kitchen"){
            saveCleaningTask("Dusting", "DUS", viewModel, preConRoom)
            saveCleaningTask("Scrub Floors", "FLO", viewModel, preConRoom)
            saveCleaningTask("Scrub Counter Tops", "COU", viewModel, preConRoom)
            saveCleaningTask("Scrub Sink", "SIN", viewModel, preConRoom)
            saveCleaningTask("Clean Microwave", "MIC", viewModel, preConRoom)
            saveCleaningTask("Clean Fridge", "Fri", viewModel, preConRoom)
            saveCleaningTask("Clean Oven", "OVE", viewModel, preConRoom)
            saveCleaningTask("Clean Cabinets/Drawers", "CAB", viewModel, preConRoom)
            saveCleaningTask("Wash Kitchen Towels/Rags", "TOW", viewModel, preConRoom)
        }
        if(preConRoom.myRoomName == "Dining Room"){
            saveCleaningTask("Dusting", "DUS", viewModel, preConRoom)
            saveCleaningTask("Scrub Floors", "FLO", viewModel, preConRoom)
            saveCleaningTask("Clean Table", "TAB", viewModel, preConRoom)
            saveCleaningTask("Wash Linens", "Lin", viewModel, preConRoom)
        }
        if(preConRoom.myRoomName == "Living Room"){
            saveCleaningTask("Dusting", "DUS", viewModel, preConRoom)
            saveCleaningTask("Vacuum", "VAC", viewModel, preConRoom)
            saveCleaningTask("Scrub Floors", "FLO", viewModel, preConRoom)
            saveCleaningTask("Clean Upholstery", "UPH", viewModel, preConRoom)
        }
        if(preConRoom.myRoomName == "Outdoors"){
            saveCleaningTask("Mow", "MOW", viewModel, preConRoom)
            saveCleaningTask("Pull Weeds", "WEE", viewModel, preConRoom)
            saveCleaningTask("Water Flowers/Garden", "WAT", viewModel, preConRoom)
            saveCleaningTask("Clean Grill", "GRI", viewModel, preConRoom)
            saveCleaningTask("Clean Deck/Porch", "DEC", viewModel, preConRoom)
            saveCleaningTask("Clean Windows", "WIN", viewModel, preConRoom)
            saveCleaningTask("Clean Siding/Brick", "GRI", viewModel, preConRoom)
            saveCleaningTask("Take Out Trash", "GRI", viewModel, preConRoom)
        }
        if(preConRoom.myRoomName == "Garage"){
            saveCleaningTask("Dusting", "DUS", viewModel, preConRoom)
            saveCleaningTask("Clean Garage Door", "VAC", viewModel, preConRoom)
            saveCleaningTask("Scrub Floors", "FLO", viewModel, preConRoom)
        }
        if(preConRoom.myRoomName == "Utility Room"){
            saveCleaningTask("Dusting", "DUS", viewModel, preConRoom)
            saveCleaningTask("Replace Furnace Filter", "VAC", viewModel, preConRoom)
            saveCleaningTask("Scrub Floors", "FLO", viewModel, preConRoom)
        }
    }

    /**
     * Creates a cleaning task and sends to viewmodel where it saves tasks to a room in database
     */
    fun saveCleaningTask(taskName: String, taskID: String, viewModel: MainViewModel, preConRoom: myRoom){
        var preConTask = cleaningTask().apply{
            cleaningTaskName = taskName
            cleaningTaskId = taskID
        }
        viewModel.saveTask(preConTask, preConRoom)
    }

    /**
     * Deletes task from the firebase.
     */
    fun deleteTask(preConRoom: myRoom, preConTask: cleaningTask) {
        user?.let {
                user->
            val document = firestore.collection("users").document(user.uid).collection("myRooms")
                .document(preConRoom.uniqueID).collection("cleaningTasks").document(preConTask.uniqueID)
            document.delete()
        }
    }

}

