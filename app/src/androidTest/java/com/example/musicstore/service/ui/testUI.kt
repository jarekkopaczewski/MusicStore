package com.example.musicstore.service.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.musicstore.MainActivity
import com.example.musicstore.R
import com.example.musicstore.service.group.AllTest
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Category(AllTest::class)
@LargeTest
class testUI {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun userInterfaceTest() {
        // login
        onView(withId(R.id.ic_profile)).perform(click())
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("maklo@gmail.com"))
        onView(withId(R.id.editTextTextPassword)).perform(typeText("321"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.loginButton)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.refreshData)).perform(click())
        Thread.sleep(3000)

        // check cart
        onView(withId(R.id.ic_cart)).perform(click())
        onView(withId(R.id.buyButton)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.resevreButton)).perform(click())
        Thread.sleep(1000)

        // add to cart
        onView(withId(R.id.ic_home)).perform(click())
        Thread.sleep(200)
        onView(withId(R.id.products_row_one)).perform(click())
        onView(withId(R.id.browseConstrain)).perform(typeText("K"))
        Thread.sleep(200)
        onView(withId(R.id.list)).perform(click())
        onView(withId(R.id.addToCartButton)).perform(click())
        onView(withId(R.id.addToCartButton)).perform(click())
        Thread.sleep(200)

        // buy items
        onView(withId(R.id.ic_cart)).perform(click())
        onView(withId(R.id.buyButton)).perform(click())
        Thread.sleep(500)
        onView(withText("Yes")).perform(click())
        Thread.sleep(2000)

        // change address
        onView(withId(R.id.ic_profile)).perform(click())
        onView(withId(R.id.cityLabel)).perform(typeText("miasto_testowe"))
        Espresso.pressBack()
        Thread.sleep(100)
        onView(withId(R.id.streetLabel)).perform(typeText("ulica_testowa"))
        Espresso.pressBack()
        Thread.sleep(100)
        onView(withId(R.id.numberBuild)).perform(typeText("12"))
        Espresso.pressBack()
        Thread.sleep(100)
        onView(withId(R.id.codeLabel)).perform(typeText("32"))
        Espresso.pressBack()
        Thread.sleep(100)
        onView(withId(R.id.updateData)).perform(click())

        // log out
        onView(withId(R.id.logoutButton)).perform(click())
        onView(withId(R.id.ic_home)).perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun employeeInterfaceTest() {
        // log in
        onView(withId(R.id.ic_profile)).perform(click())
        onView(withId(R.id.employeeLogin)).perform(click())
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("krajan@gmail.com"))
        onView(withId(R.id.editTextTextPassword)).perform(typeText("gryll"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withId(R.id.loginButton)).perform(click())
        Thread.sleep(1000)

        onView(withId(R.id.ic_home)).perform(click())
        onView(withId(R.id.browseConstrain2)).perform(typeText("I"))
        Espresso.pressBack()
        Thread.sleep(2000)

        onView(withId(R.id.ic_cart)).perform(click())
        onView(withId(R.id.browseConstrain3)).perform(typeText("wy"))
        Espresso.pressBack()
        Thread.sleep(3000)

        // log out
        onView(withId(R.id.ic_profile)).perform(click())
        onView(withId(R.id.logoutButton)).perform(click())
        onView(withId(R.id.ic_home)).perform(click())
        Thread.sleep(1000)
    }

    fun pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}
