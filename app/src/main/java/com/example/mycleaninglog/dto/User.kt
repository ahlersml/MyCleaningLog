package com.example.mycleaninglog.dto

data class User(val uid: String = "", var displayName: String?){

    override fun toString(): String {
        return "$displayName"
    }

}
