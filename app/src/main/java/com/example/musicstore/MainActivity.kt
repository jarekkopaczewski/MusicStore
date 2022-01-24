package com.example.musicstore

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.musicstore.fragments.*
import com.example.musicstore.service.DataBaseSupport
import com.example.musicstore.service.LoginInterface
import com.example.musicstore.data.Type
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val productsFragment = ProductsFragment()
    private val employeeFragment = EmployeeFragment()
    private val ordersFragment = OrdersFragment()
    private val profileFragment = ProfileFragment()
    private val cartFragment = CartFragment()
    private val loggedInFragment = LoggedInFragment()
    private lateinit var bottomNavigation: BottomNavigationView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        DataBaseSupport.getCategoriesFromBase(this)
        replaceFragment(productsFragment)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    when (LoginInterface.getType()) {
                        Type.K -> replaceFragment(productsFragment)
                        Type.M -> replaceFragment(employeeFragment)
                        Type.S -> replaceFragment(employeeFragment)
                    }
                }
                R.id.ic_cart -> {
                    when (LoginInterface.getType()) {
                        Type.K -> replaceFragment(cartFragment)
                        Type.M -> replaceFragment(ordersFragment)
                        Type.S -> replaceFragment(ordersFragment)
                    }
                }
                R.id.ic_profile -> {
                    when (LoginInterface.getStatus()) {
                        true -> replaceFragment(loggedInFragment)
                        false -> replaceFragment(profileFragment)
                    }
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment?) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}