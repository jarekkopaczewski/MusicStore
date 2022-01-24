package com.example.musicstore.service.logic

import android.widget.ImageView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.musicstore.service.AnimateView
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
class AnimateViewTest {

    @Test
    fun checkAnimation() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val testView = ImageView(appContext)
        var thrown = false

        try {
            AnimateView.animateInOut(testView, appContext)
        } catch (e: Exception) {
            thrown = true
        }
        assertFalse(thrown)
    }
}