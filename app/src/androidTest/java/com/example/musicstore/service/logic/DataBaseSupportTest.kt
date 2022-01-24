package com.example.musicstore.service.logic

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.musicstore.service.DataBaseSupport
import com.example.musicstore.service.LoginInterface
import com.example.musicstore.service.group.BasicLogicTest
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@Category(BasicLogicTest::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DataBaseSupportTest {

    @Test
    fun loginCheck() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        LoginInterface.setStatus(false)
        DataBaseSupport.checkLogin(appContext, "maklo@gmail.com", "321")
        Thread.sleep(500)
        assertEquals(true, LoginInterface.getStatus())
    }

    @Test
    fun wrongLoginCheck() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        LoginInterface.setStatus(false)
        DataBaseSupport.checkLogin(appContext, "maklo@onet.com", "321")
        Thread.sleep(500)
        assertEquals(false, LoginInterface.getStatus())
    }

    @Test
    fun getProducts() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        DataBaseSupport.getProductsFromBase(appContext, "ukulele")
        Thread.sleep(500)
        assertTrue(DataBaseSupport.getProductsData().size > 0)
    }

    @Test
    fun getWrongProducts() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        DataBaseSupport.getProductsFromBase(appContext, "telewizory")
        Thread.sleep(500)
        assertTrue(DataBaseSupport.getProductsData().size == 0)
    }
}