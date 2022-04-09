package com.example.mycleaninglog

import com.example.mycleaninglog.service.CleaningService
import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import com.example.mycleaninglog.service.RoomService
import kotlinx.coroutines.newSingleThreadContext
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

class CleaningTests {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm:MainViewModel
    lateinit var cleaningService: CleaningService
    lateinit var roomService : RoomService
    var allMyTasks : List<cleaningTask>? = ArrayList<cleaningTask>()
    var allMyRooms : List<myRoom>? = ArrayList<myRoom>()

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
    fun `given rooms are available when i add a room it should appear in the list`() = runTest{
        givenRoomServiceIsInitialized()
        whenRoomDataIsReadAndParsed()
        thenRoomsAreAvailable()
    }

    private fun givenRoomServiceIsInitialized(){
        roomService = RoomService()
    }

    private suspend fun whenRoomDataIsReadAndParsed(){
        allMyRooms = roomService.fetchRooms()
    }

    private fun thenRoomsAreAvailable(){
        assertNotNull(allMyRooms)
        assertTrue(allMyRooms!!.isNotEmpty())
    }

    @Test
    fun `given tasks are available when i add a task it should appear in the list`() = runTest{
        givenCleaningServiceIsInitialized()
        whenTaskDataIsReadAndParsed()
        thenTasksAreAvailable()
    }

    private fun givenCleaningServiceIsInitialized(){
        cleaningService = CleaningService()
    }

    private suspend fun whenTaskDataIsReadAndParsed(){
        allMyTasks = cleaningService.fetchTasks()
    }

    private fun thenTasksAreAvailable(){
        assertNotNull(allMyTasks)
        assertTrue(allMyTasks!!.isNotEmpty())
    }
}