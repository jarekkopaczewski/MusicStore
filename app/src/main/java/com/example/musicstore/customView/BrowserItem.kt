package com.example.musicstore.customView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicstore.R

class BrowserItem(context: Context?) : ConstraintLayout(context!!) {
    private var name: TextView
    private var company: TextView
    private var price: TextView
    private var number: TextView

    init {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.browser_item, this, true)
        name = view.findViewById(R.id.productName)
        company = view.findViewById(R.id.company)
        price = view.findViewById(R.id.priceText)
        number = view.findViewById(R.id.confirmButton)
    }

    @SuppressLint("SetTextI18n")
    fun setText(name: String, company: String, price: Int) {
        this.name.text = name
        this.company.text = company
        this.price.text = "$price PLN"
    }

    @SuppressLint("SetTextI18n")
    fun setText(name: String, company: String, price: Int, number: Int) {
        this.name.text = name
        this.company.text = company
        this.price.text = "$price PLN"
        this.number.text = "Ilość: $number"
    }
}