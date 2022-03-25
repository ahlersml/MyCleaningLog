package com.example.mycleaninglog.dto

import com.google.firebase.firestore.Exclude

data class cleaningTask(var cleaningTaskName:String = "", var cleaningTaskId:String = "", var uniqueID:String = ""){

    override fun toString(): String {
        return "$cleaningTaskName"
    }
}
