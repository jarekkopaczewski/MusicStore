package com.example.musicstore

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
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var orders : ArrayList<Order> = arrayListOf()
    private var category : String = ""
    private var searchConstrain : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_orders2, container, false)
        val list : LinearLayout = view.findViewById(R.id.list_orders)
        val refresh : SwipeRefreshLayout = view.findViewById(R.id.refreshLayout_orders)
        val browser : EditText = view.findViewById(R.id.browseConstrain3)

        refresh.setOnRefreshListener {
            list.removeAllViews()
            for(produkt in orders)
            {
                if( produkt.id_zamowienia.toString().contains(searchConstrain) || produkt.status.contains(searchConstrain) || searchConstrain == "")
                {
                    val newText = BrowserItemButton(context)
                    newText.setText(produkt.id_zamowienia, produkt.status, produkt.wartosc)
                    newText.setType(true)
                    newText.setOnClickListener {
                        animateInOut(newText)
                    }
                    list.addView(newText)
                }
            }
            refresh.isRefreshing = false
        }

        view.post(Thread{
            context?.let { it1 -> DataBaseSupport.getOrdersFromBase(it1) }
            orders = DataBaseSupport.getOrders()
            browser.hint = "Szukaj w ${category.decapitalize()}"
        })

        browser.doOnTextChanged { text, start, before, count ->
            searchConstrain = browser.text.toString()
            list.removeAllViews()
            for(produkt in orders)
            {
                if( produkt.id_zamowienia.toString().contains(searchConstrain) || produkt.status.contains(searchConstrain) || searchConstrain == "")
                {
                    val newText = BrowserItemButton(context)
                    newText.setText(produkt.id_zamowienia, produkt.status, produkt.wartosc)
                    newText.setType(true)
                    newText.setOnClickListener {
                        animateInOut(newText)
                    }
                    list.addView(newText)
                }
            }
        }

        return view
    }

    private fun animateInOut(button: View)
    {
        val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        button.startAnimation(zoomIn)
        button.startAnimation(zoomOut)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrdersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}