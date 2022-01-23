package com.example.musicstore.service

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.musicstore.data.*
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class DataBaseSupport {

    companion object {
        private const val ip: String = "192.168.0.32"
        private var categoriesList: ArrayList<String> = arrayListOf()
        private var productsList: ArrayList<ProductData> = arrayListOf()
        private var employeeItems: ArrayList<ProductSM> = arrayListOf()
        private var orders: ArrayList<Order> = arrayListOf()
        private lateinit var queue: RequestQueue
        private lateinit var currentAddress: Address

        fun getCategoriesFromBase(context: Context) {
            categoriesList.clear()
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://$ip/get_full_table.php?tableName=kategorie&selectedRows=kategoria",
                {
                    println(it);
                    val stringArray = java.util.ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for (item in stringArray)
                        categoriesList.add(item.get("kategoria").toString())
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
        }

        fun getProductsFromBase(context: Context, category: String) {
            productsList.clear()
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://$ip/get_conntent_from_view.php?category=$category",
                {
                    val stringArray = ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for ((i, _) in stringArray.withIndex()) {
                        productsList.add(Gson().fromJson(stringArray[i].toString(), ProductData::class.java))
                    }

                    println(" produkty ${productsList.size}")
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
        }

        fun checkLogin(context: Context, email: String, pass: String): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://$ip/login.php?email=$email&pass=$pass",
                { mes ->
                    if (!mes.equals("null")) {
                        LoginInterface.setType(Type.K)
                        LoginInterface.setStatus(true)
                        LoginInterface.setClientID(mes.filter { it.isDigit() }.toInt())
                        println(LoginInterface.getClientID())
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun checkEmployeeLogin(context: Context, email: String, pass: String): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://$ip/loginEmp.php?email=$email&pass=$pass",
                {
                    if (!it.equals("null")) {
                        if (it.contains("M"))
                            LoginInterface.setType(Type.M)
                        else
                            LoginInterface.setType(Type.S)
                        LoginInterface.setStatus(true)
                        Toast.makeText(context, "Login successful (emp)", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Login failed (emp)", Toast.LENGTH_SHORT).show()
                    }
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun getEmployeeItemsFromBase(context: Context): Boolean {
            employeeItems.clear()
            queue = Volley.newRequestQueue(context)
            var urlReq = "";

            if (LoginInterface.getType() == Type.M)
                urlReq = "http://$ip/get_employee_conntent.php?type=magazyn";
            else if (LoginInterface.getType() == Type.S)
                urlReq = "http://$ip/get_employee_conntent.php?type=sklep";

            val request = StringRequest(
                Request.Method.GET, urlReq,
                {
                    val stringArray = ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for ((i, _) in stringArray.withIndex()) {
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

        fun getOrdersFromBase(context: Context): Boolean {
            orders.clear()
            queue = Volley.newRequestQueue(context)

            val request = StringRequest(
                Request.Method.GET, "http://$ip/get_employee_orders.php",
                {
                    val stringArray = java.util.ArrayList<JSONObject>()
                    val jsonArray = JSONArray(it)

                    for (i in 0 until jsonArray.length())
                        stringArray.add(jsonArray.get(i) as JSONObject)

                    for ((i, _) in stringArray.withIndex()) {
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

        fun updateData(context: Context, miasto: String, ulica: String, numer: String, kod: String): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET,
                "http://$ip/updateData.php?miasto=$miasto&ulica=$ulica&numer=$numer&kod=$kod&id=${LoginInterface.getClientID()}",
                {
                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show()
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun getAddressBase(context: Context): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://192.168.0.32/get_address.php?id=${LoginInterface.getClientID()}",
                {
                    if (LoginInterface.getType() == Type.K) {
                        currentAddress = Gson().fromJson(it, Address::class.java)
                        if (currentAddress.miasto != "" && currentAddress.ulica != "" && currentAddress.numer_domu != 0 && currentAddress.kod_pocztowy != "")
                            LoginInterface.setAddressState(true)
                        println("pobrano adres.")
                    }
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun updateItemInStore(context: Context, amount: Int, kod: String): Boolean {
            var sqlReq = if (LoginInterface.getType() == Type.M) {
                "http://$ip/update_amount.php?ilosc=$amount&kod=$kod&tabela=MAGAZYN"
            } else {
                "http://$ip/update_amount.php?ilosc=$amount&kod=$kod&tabela=SKLEP"
            }
            queue = Volley.newRequestQueue(context)

            val request = StringRequest(
                Request.Method.GET, sqlReq,
                {
                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show()
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun updateOrderStatus(context: Context, code: Int, id: Int): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://$ip/update_order.php?typ=$code&id=$id&tabela=SKLEP",
                {
                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show()
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun addOrder(context: Context, code: Int): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://$ip/add_order.php?typ=$code&id=${LoginInterface.getClientID()}",
                {
                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show()
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun addOrderData(context: Context, kod: String, ilosc: Int, status: Int): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET,
                "http://$ip/add_order_2.php?id_k=${LoginInterface.getClientID()}&kod=$kod&ilosc=$ilosc&status=$status",
                {
                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show()
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return false
        }

        fun getCategories(): ArrayList<String> = categoriesList
        fun getProductsData(): ArrayList<ProductData> = productsList
        fun getEmployeeItems(): ArrayList<ProductSM> = employeeItems
        fun getOrders(): ArrayList<Order> = orders
        fun getAddress(): Address = currentAddress
    }
}