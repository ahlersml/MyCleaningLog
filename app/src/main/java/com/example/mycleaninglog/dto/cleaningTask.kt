package com.example.mycleaninglog.dto

import com.google.firebase.firestore.Exclude

data class Task(var taskName:String = "", var taskId:String = ""){

    override fun toString(): String {
        return "$taskName"
    }
}
