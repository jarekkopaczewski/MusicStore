package com.example.musicstore.service

import android.content.Context
import android.widget.Toast
import com.example.musicstore.data.ProductData
import java.util.ArrayList

class Cart {
    companion object {
        private var productsList: ArrayList<ProductData> = arrayListOf()
        private var countList: ArrayList<Int> = arrayListOf()

        fun addProduct(product: ProductData, context: Context) {
            if (productsList.contains(product)) {
                countList[productsList.indexOf(product)]++
                Toast.makeText(context, "Item added to your cart", Toast.LENGTH_SHORT).show()

            } else {
                productsList.add(product)
                countList.add(1)
                Toast.makeText(context, "Item added to your cart", Toast.LENGTH_SHORT).show()
            }
        }

        fun deleteProduct(product: ProductData, context: Context) {
            if (countList[productsList.indexOf(product)] == 1) {
                countList.removeAt(productsList.indexOf(product))
                productsList.remove(product)
                Toast.makeText(context, "Item removed from your cart", Toast.LENGTH_SHORT).show()
            } else if (countList[productsList.indexOf(product)] > 1) {
                countList[productsList.indexOf(product)]--
                Toast.makeText(context, "Item removed from your cart", Toast.LENGTH_SHORT).show()
            }
        }

        fun getProducts(): ArrayList<ProductData> = productsList
        fun getCount(): ArrayList<Int> = countList
    }
}