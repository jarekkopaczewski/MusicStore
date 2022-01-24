package com.example.musicstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicstore.R
import com.example.musicstore.customView.BrowserItemButton
import com.example.musicstore.data.Order
import com.example.musicstore.service.AnimateView
import com.example.musicstore.service.DataBaseSupport
import java.util.*

class OrdersFragment : Fragment() {
    private var orders: ArrayList<Order> = arrayListOf()
    private var searchConstrain: String = ""
    private lateinit var list: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_orders2, container, false)
        list = view.findViewById(R.id.list_orders)
        val refresh: SwipeRefreshLayout = view.findViewById(R.id.refreshLayout_orders)
        val browser: EditText = view.findViewById(R.id.browseConstrain3)

        refresh.setOnRefreshListener {
            list.removeAllViews()
            refresh()
            refresh.isRefreshing = false
        }

        view.post(Thread {
            context?.let { it1 -> DataBaseSupport.getOrdersFromBase(it1) }
            orders = DataBaseSupport.getOrders()
            browser.hint = "Szukaj w zamowieniach"
            refresh()
        })

        browser.doOnTextChanged { _, _, _, _ ->
            searchConstrain = browser.text.toString()
            refresh()
            list.removeAllViews()
        }
        return view
    }

    private fun refresh()
    {
        for (produkt in orders) {
            if (produkt.id_zamowienia.toString().contains(searchConstrain) ||
                produkt.status.contains(searchConstrain) ||
                produkt.status.contains(searchConstrain) || searchConstrain == "") {
                val newText = BrowserItemButton(context)
                newText.setText(produkt.id_zamowienia, produkt.status, produkt.wartosc)
                newText.setType(true)
                newText.setOnClickListener {
                    AnimateView.animateInOut(newText, context)
                }
                list.addView(newText)
            }
        }
    }
}