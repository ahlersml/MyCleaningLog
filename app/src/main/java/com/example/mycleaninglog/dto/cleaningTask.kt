package com.example.mycleaninglog.dto

import com.google.firebase.firestore.Exclude

data class Task(var TaskName:String = "", var TaskId:String = "", var uniqueID:String = ""){

    override fun toString(): String {
        return "$TaskName"
    }
}
