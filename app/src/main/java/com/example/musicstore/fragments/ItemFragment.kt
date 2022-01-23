package com.example.musicstore.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.*
import com.example.musicstore.R
import com.example.musicstore.data.ProductData
import com.example.musicstore.data.ProductSM
import com.example.musicstore.service.AnimateView
import com.example.musicstore.service.Cart

class ItemFragment : Fragment() {
    private lateinit var product: ProductData
    private lateinit var shopProductSM: ProductSM
    private var employee = false
    private var type: Int = 0

    fun setProduct(product: ProductData) {
        this.product = product
        this.type = 0
    }

    fun setProduct(product: ProductSM) {
        this.shopProductSM = product
        this.type = 1
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.item_site, container, false)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemCompany: TextView = view.findViewById(R.id.itemCompany)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val orderButton: ImageView = view.findViewById(R.id.addToCartButton)

        if (employee) {
            orderButton.visibility = INVISIBLE
            orderButton.isEnabled = false
        }

        if (type == 1) {
            itemName.text = shopProductSM.nazwa
            itemCompany.text = shopProductSM.kod_kreskowy
            itemPrice.text = shopProductSM.ilosc.toString()
        } else {
            itemName.text = product.nazwa
            itemCompany.text = product.producent
            itemPrice.text = product.cena.toString()
        }

        orderButton.setOnClickListener {
            AnimateView.animateInOut(orderButton, context)
            context?.let { it1 -> Cart.addProduct(product, it1) }
        }
        return view
    }

    fun turnOffButton() {
        this.employee = true
    }
}