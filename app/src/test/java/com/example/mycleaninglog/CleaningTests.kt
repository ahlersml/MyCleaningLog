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
import kotlinx.coroutines.runBlocking
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
    var allMyTasks = ArrayList<cleaningTask>()
    var allMyRooms = ArrayList<myRoom>()

    //private val mainThreadSurrogate = newSingleThreadContext("UI thread")

   // @MockK
   // lateinit var mockCleaningService: CleaningService

   /*@Before
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
*/
    @Test
    fun `when i add a room it should appear in the list`() = runTest{
        whenIAddRoom()
        thenRoomsAreAvailable()
    }

    private fun whenIAddRoom(){
        allMyRooms.add(myRoom("Bed Room"))
        allMyRooms.add(myRoom("Kitchen"))
    }

    private fun thenRoomsAreAvailable() {
        assertNotNull(allMyRooms)
        var containsBedroom = false
        allMyRooms?.let{
            it.forEach{
                if(it.myRoomName.equals("Bed Room")){
                    containsBedroom = true
                }
            }
            assertTrue(containsBedroom)
        }
    }

    @Test
    fun `given tasks are available when i add a task it should appear in the list`() = runTest(){

        whenIAddCleaningTask()
        thenCleaningTaskAreAvailable()
    }

    private fun whenIAddCleaningTask() {
        allMyTasks.add(cleaningTask("Vacuum Floor"))
        allMyTasks.add(cleaningTask("Dust Room"))
    }

    private fun thenCleaningTaskAreAvailable() {
        assertNotNull(allMyTasks)
        var containsDustRoom = false
        allMyTasks?.let{
            it.forEach{
                if(it.cleaningTaskName.equals("Dust Room")){
                    containsDustRoom = true
                }
            }
            assertTrue(containsDustRoom)
        }
    }

}