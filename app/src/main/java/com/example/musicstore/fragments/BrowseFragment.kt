package com.example.musicstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicstore.service.DataBaseSupport
import com.example.musicstore.data.ProductData
import com.example.musicstore.R
import com.example.musicstore.customView.BrowserItem
import com.example.musicstore.service.AnimateView
import java.util.*

class BrowseFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var productsList: ArrayList<ProductData> = arrayListOf()
    private var category: String = ""
    private var searchConstrain: String = ""

    fun setCategory(category: String) {
        this.category = category
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_browse, container, false)
        val list: LinearLayout = view.findViewById(R.id.list)
        val refresh: SwipeRefreshLayout = view.findViewById(R.id.refreshLayout)
        val browser: EditText = view.findViewById(R.id.browseConstrain)

        refresh.setOnRefreshListener {
            list.removeAllViews()
            for (produkt in productsList) {
                if (produkt.producent.contains(searchConstrain) || produkt.nazwa.contains(searchConstrain) || searchConstrain == "") {
                    val newText = BrowserItem(context)
                    newText.setText(produkt.nazwa, produkt.producent, produkt.cena)
                    newText.setOnClickListener {
                        AnimateView.animateInOut(newText,context)
                        val fragment = ItemFragment()
                        fragment.setProduct(produkt)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace((view.parent as ViewGroup).id, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                    list.addView(newText)
                }
            }
            refresh.isRefreshing = false
        }

        view.post(Thread {
            context?.let { it1 -> DataBaseSupport.getProductsFromBase(it1, category) }
            productsList = DataBaseSupport.getProductsData()
            browser.hint = "Szukaj w ${category.replaceFirstChar { it.lowercase(Locale.getDefault()) }}"
        })

        browser.doOnTextChanged { _, _, _, _ ->
            searchConstrain = browser.text.toString()
            list.removeAllViews()
            for (produkt in productsList) {
                if (produkt.producent.lowercase(Locale.getDefault())
                        .contains(searchConstrain.lowercase(Locale.getDefault())) ||
                    produkt.nazwa.lowercase(Locale.getDefault())
                        .contains(searchConstrain.lowercase(Locale.getDefault())) || searchConstrain == ""
                ) {
                    val newText = BrowserItem(context)
                    newText.setText(produkt.nazwa, produkt.producent, produkt.cena)

                    newText.setOnClickListener {
                        AnimateView.animateInOut(newText,context)
                        val fragment = ItemFragment()
                        fragment.setProduct(produkt)
                        activity!!.supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace((view.parent as ViewGroup).id, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                    list.addView(newText)
                }
            }
        }
        return view
    }
}