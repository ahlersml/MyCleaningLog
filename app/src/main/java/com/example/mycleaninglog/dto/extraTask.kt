package com.example.mycleaninglog.dto

import com.google.firebase.firestore.Exclude

data class extraTask(var extraTaskName:String = "", var extraTaskId:String = "", var uniqueID:String = ""){

    override fun toString(): String {
        return "$extraTaskName"
    }
}
