package com.example.musicstore.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicstore.*
import com.example.musicstore.customView.BrowserItem
import com.example.musicstore.data.ProductData
import com.example.musicstore.data.Type
import com.example.musicstore.service.*

class CartFragment : Fragment() {
    private lateinit var productsList: ArrayList<ProductData>
    private lateinit var countList: ArrayList<Int>
    private lateinit var list: LinearLayout
    private lateinit var refresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_cart, container, false)

        list = view.findViewById(R.id.cartLayout)
        refresh = view.findViewById(R.id.refreshCart)
        val buy: Button = view.findViewById(R.id.buyButton)
        val reserve: Button = view.findViewById(R.id.resevreButton)

        if (LoginInterface.getStatus() && LoginInterface.getType() == Type.K) {
            buy.isEnabled = true
            reserve.isEnabled = true
        } else {
            buy.isEnabled = false
            reserve.isEnabled = false
        }

        refresh = view.findViewById(R.id.refreshCart)

        view.post(Thread {
            productsList = Cart.getProducts()
            countList = Cart.getCount()
            refreshItems()
        })

        buy.setOnClickListener {
            AnimateView.animateInOut(buy,context)
            if (Cart.getProducts().size == 0) Toast.makeText(context, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            else if (LoginInterface.getAddressState()) showDialog(3)
            else Toast.makeText(context, "Your address is missing", Toast.LENGTH_SHORT).show()
        }

        reserve.setOnClickListener {
            AnimateView.animateInOut(reserve,context)
            if (Cart.getProducts().size == 0) Toast.makeText(context, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            else showDialog(4)
        }

        refresh.setOnRefreshListener {
            refreshItems()
            refresh.isRefreshing = false
        }

        return view
    }

    private fun showDialog(code: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm")
        builder.setMessage("Are you sure?")
        builder.setPositiveButton("Yes") { dialog, _ ->

            context?.let { it1 -> DataBaseSupport.addOrder(it1, code) }
            calcCart(code, context)
            Toast.makeText(context, "You will get email with confirm", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun calcCart(status: Int, con: Context?) {
        val productsList: ArrayList<ProductData> = Cart.getProducts()
        val countList: ArrayList<Int> = Cart.getCount()

        for (i in 0 until productsList.size) {
            con?.let { it1 -> DataBaseSupport.addOrderData(it1, productsList[i].kod_kreskowy, countList[i], status) }
        }
    }

    private fun refreshItems() {
        productsList = Cart.getProducts()
        countList = Cart.getCount()
        list.removeAllViews()
        for (product in productsList) {
            val newText = BrowserItem(context)
            newText.setText(product.nazwa, product.producent, product.cena, countList[productsList.indexOf(product)])
            newText.setOnClickListener {
                AnimateView.animateInOut(newText,context)
                context?.let { it1 -> Cart.deleteProduct(product, it1) }
                refreshItems()
            }
            list.addView(newText)
        }
    }
}