package com.example.musicstore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val productsFragment = ProductsFragment()
    private val warehouseFragment = WarehouseFragment()
    private val storeFragment = StoreFragment()
    private val profileFragment = ProfileFragment()
    private val cartFragment = CartFragment()
    private lateinit var bottomNavigation : BottomNavigationView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        replaceFragment(productsFragment)
        DataBaseSupport.getCategoriesFromBase(this)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> {
                    when(LoginInterface.getType())
                    {
                        Type.K -> replaceFragment(productsFragment)
                        Type.M -> replaceFragment(warehouseFragment)
                        Type.S -> replaceFragment(storeFragment)
                    }
                }
                R.id.ic_cart -> {replaceFragment(cartFragment)}
                R.id.ic_profile -> {replaceFragment(profileFragment)}
        }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment?){
        if(fragment!=null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}