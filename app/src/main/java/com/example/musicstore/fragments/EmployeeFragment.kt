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
import com.example.musicstore.data.ProductSM
import com.example.musicstore.service.AnimateView
import com.example.musicstore.service.DataBaseSupport
import java.util.*

class EmployeeFragment : Fragment() {
    private var productsList: ArrayList<ProductSM> = arrayListOf()
    private var category: String = ""
    private var searchConstrain: String = ""
    private lateinit var list: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_warehouse, container, false)
        list = view.findViewById(R.id.list_warehouse)
        val refresh: SwipeRefreshLayout = view.findViewById(R.id.refreshLayout2)
        val browser: EditText = view.findViewById(R.id.browseConstrain2)

        view.post(Thread {
            context?.let { it1 -> DataBaseSupport.getEmployeeItemsFromBase(it1) }
            productsList = DataBaseSupport.getEmployeeItems()
            browser.hint = "Szukaj w ${category.replaceFirstChar { it.lowercase(Locale.getDefault()) }}"
            refreshProducts(view)
        })

        refresh.setOnRefreshListener {
            list.removeAllViews()
            view.post(Thread {
                refreshProducts(view)
            })
            refresh.isRefreshing = false
        }

        browser.doOnTextChanged { _, _, _, _ ->
            searchConstrain = browser.text.toString()
            view.post(Thread {
                refreshProducts(view)
            })
            list.removeAllViews()
        }
        return view
    }

    private fun refreshProducts(view: View) {
        for (produkt in productsList) {
            if (produkt.kod_kreskowy.contains(searchConstrain) || produkt.nazwa.contains(searchConstrain) || searchConstrain == "") {
                val newText = BrowserItemButton(context)
                newText.setText(produkt.nazwa, produkt.kod_kreskowy, produkt.ilosc.toString())
                newText.setKod(produkt.kod_kreskowy)
                newText.setOnClickListener {
                    AnimateView.animateInOut(newText, context)

                    val fragment = ItemFragment()
                    fragment.setProduct(produkt)
                    fragment.turnOffButton()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace((view.parent as ViewGroup).id, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                list.addView(newText)
            }
        }
    }
}