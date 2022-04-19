package com.example.mycleaninglog

import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mycleaninglog.dto.cleaningTask
import com.example.mycleaninglog.dto.myRoom
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*

class CleaningTests {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm:MainViewModel
    var allMyTasks = ArrayList<cleaningTask>()
    var allMyRooms = ArrayList<myRoom>()

    @Test
    fun `given rooms are available when i add a room it should appear in the list`() = runTest{
        givenRoomServiceIsInitialized()
        whenRoomDataIsReadAndParsed()
        thenRoomsAreAvailable()
    }

    private fun givenRoomServiceIsInitialized() {
        allMyRooms = ArrayList<myRoom>()
    }

    private fun whenRoomDataIsReadAndParsed() {
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
    fun `given rooms are available when i delete a room it should remove from the list`() = runTest{
        givenRoomListIsInitialized()
        whenRoomDataIsRemoved()
        thenRoomRemovedFromList()
    }

    private fun givenRoomListIsInitialized() {
        allMyRooms = ArrayList<myRoom>()
        allMyRooms.add(myRoom("Bed Room"))
        allMyRooms.add(myRoom("Kitchen"))
    }

    private fun whenRoomDataIsRemoved() {
        allMyRooms.remove(myRoom("Bed Room"))
    }

    private fun thenRoomRemovedFromList() {
        assertNotNull(allMyRooms)
        var containsBedroom = false
        allMyRooms?.let{
            it.forEach{
                if(it.myRoomName.equals("Bed Room")){
                    containsBedroom = true
                }
            }
            assertFalse(containsBedroom)
        }
    }


    @Test
    fun `given tasks are available when i add a task it should appear in the list`() = runTest(){
        givenCleaningServiceIsInitialized()
        whenIAddCleaningTask()
        thenCleaningTaskAreAvailable()
    }

    private fun givenCleaningServiceIsInitialized() {
        allMyTasks = ArrayList<cleaningTask>()
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

    @Test
    fun `given tasks are available when i delete a task it should remove from the cleaning task list`() = runTest{
        givenCleaningTaskListIsInitialized()
        whenCleaningTaskIsRemoved()
        thenCleaningTaskIsRemovedFromList()
    }

    private fun givenCleaningTaskListIsInitialized() {
        allMyTasks = ArrayList<cleaningTask>()
        allMyTasks.add(cleaningTask("Vacuum Floor"))
        allMyTasks.add(cleaningTask("Dust Room"))
    }

    private fun whenCleaningTaskIsRemoved() {
        allMyTasks.remove(cleaningTask("Dust Room"))
    }

    private fun thenCleaningTaskIsRemovedFromList() {
        assertNotNull(allMyTasks)
        var containsDustRoom = false
        allMyTasks?.let{
            it.forEach{
                if(it.cleaningTaskName.equals("Dust Room")){
                    containsDustRoom = true
                }
            }
            assertFalse(containsDustRoom)
        }
    }
}