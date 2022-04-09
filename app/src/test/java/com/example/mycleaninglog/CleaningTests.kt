package com.example.mycleaninglog

import com.example.mycleaninglog.service.CleaningService
import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mycleaninglog.dto.cleaningTask
import kotlinx.coroutines.newSingleThreadContext
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class CleaningTests {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm:MainViewModel
    lateinit var cleaningService: CleaningService
    var allTasks : List<cleaningTask>? = ArrayList<cleaningTask>()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @MockK
    lateinit var mockCleaningService: CleaningService

    @Before
    fun populateTasks() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        mvm = MainViewModel()

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `given a cleaning task dto when name is Vacuum and id is VAC then name is Vacuum and id is VAC`() {
        var cleaningTask = cleaningTask("Vacuum", "VAC")
        Assert.assertTrue(cleaningTask.cleaningTaskName.equals("Vacuum"))
        Assert.assertTrue(cleaningTask.cleaningTaskId.equals("VAC"))
    }

    @Test
    fun `given a cleaning task dto when name is Vacuum and id is VAC then output is Vacuum VAC`() {
        var cleaningTask = cleaningTask("Vacuum", "VAC")
        Assert.assertTrue(cleaningTask.toString().equals("Vacuum VAC"))
    }

}