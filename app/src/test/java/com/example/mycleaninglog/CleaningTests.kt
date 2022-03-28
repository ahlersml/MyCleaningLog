package com.example.mycleaninglog

import com.example.mycleaninglog.service.CleaningService
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

class CleaningTests {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm:MainViewModel

    lateinit var cleaningService: CleaningService

    @Test
    fun successfullyAddRoom(){
        isDataAvailable()
    }

    private fun isDataAvailable() {
        mvm = MainViewModel()
    }
}