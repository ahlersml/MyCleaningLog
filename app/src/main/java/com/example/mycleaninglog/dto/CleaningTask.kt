package com.example.mycleaninglog.dto

import com.google.firebase.firestore.Exclude

data class CleaningTask(var cleaningTaskName:String = "", var cleaningTaskId:String = ""){

    override fun toString(): String {
        return "$cleaningTaskName"
    }
}
