package com.example.musicstore

import android.content.Context
import android.media.audiofx.LoudnessEnhancer
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class DataBaseSupport {

    companion object
    {
        private var categoriesList : ArrayList<String> = arrayListOf()
        private var productsList : ArrayList<ProductData> = arrayListOf()
        private var employeeItems : ArrayList<ProductSM> = arrayListOf()
        private var orders : ArrayList<Order> = arrayListOf()
        private lateinit var queue :RequestQueue

        fun getCategoriesFromBase(context : Context)
        {
            categoriesList.clear()
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://192.168.0.32/get_full_table.php?tableName=kategorie&selectedRows=kategoria",
                {
                    println(it);
                    val stringArray = java.util.ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for(item in stringArray)
                        categoriesList.add (item.get("kategoria").toString())
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
        }

        fun getProductsFromBase(context : Context, category : String)
        {
            productsList.clear()
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://192.168.0.32/get_conntent_from_view.php?category=$category",
                {
                    val stringArray = ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for((i, item) in stringArray.withIndex())
                    {
                        productsList.add(Gson().fromJson(stringArray[i].toString(), ProductData::class.java))
                    }

                    println(" produkty ${productsList.size}")
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
        }

        fun checkLogin(context : Context, email : String, pass : String) : Boolean
        {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://192.168.0.32/login.php?email=$email&pass=$pass",
                {
                    if(!it.equals("null"))
                    {
                        LoginInterface.setType(Type.K)
                        LoginInterface.setStatus(true)
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun checkEmployeeLogin(context : Context, email : String, pass : String) : Boolean
        {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://192.168.0.32/loginEmp.php?email=$email&pass=$pass",
                {
                    if(!it.equals("null"))
                    {
                        if( it.contains("M"))
                            LoginInterface.setType(Type.M)
                        else
                            LoginInterface.setType(Type.S)
                        LoginInterface.setStatus(true)
                        Toast.makeText(context, "Login successful (emp)", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(context, "Login failed (emp)", Toast.LENGTH_SHORT).show()
                    }
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun getEmployeeItemsFromBase(context : Context) : Boolean
        {
            employeeItems.clear()
            queue = Volley.newRequestQueue(context)
            var urlReq = "";

            if(LoginInterface.getType() == Type.M)
                urlReq = "http://192.168.0.32/get_employee_conntent.php?type=magazyn";
            else if(LoginInterface.getType() == Type.S)
                urlReq = "http://192.168.0.32/get_employee_conntent.php?type=sklep";

            val request = StringRequest(
                Request.Method.GET, urlReq,
                {
                    val stringArray = ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for((i, item) in stringArray.withIndex())
                    {
                        employeeItems.add(Gson().fromJson(stringArray[i].toString(), ProductSM::class.java))
                    }

                    println(" produkty ${employeeItems.size}")
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun getOrdersFromBase(context : Context) : Boolean
        {
            orders.clear()
            queue = Volley.newRequestQueue(context)

            val request = StringRequest(
                Request.Method.GET, "http://192.168.0.32/get_employee_orders.php",
                {
                    val stringArray = java.util.ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for((i, item) in stringArray.withIndex())
                    {
                        orders.add(Gson().fromJson(stringArray[i].toString(), Order::class.java))
                    }

                    println(" produkty ${employeeItems.size}")
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun getCategories() : ArrayList<String> =  categoriesList
        fun getProductsData() : ArrayList<ProductData> =  productsList
        fun getEmployeeItems() : ArrayList<ProductSM> =  employeeItems
        fun getOrders() : ArrayList<Order> = orders
        fun getCategoriesSize(): Int = categoriesList.size
    }
}