package com.example.musicstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var productsList : ArrayList<ProductData>
    private lateinit var countList : ArrayList<Int>
    private lateinit var list : LinearLayout
    private lateinit var refresh : SwipeRefreshLayout

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
        val view : View =  inflater.inflate(R.layout.fragment_cart, container, false)

        list = view.findViewById(R.id.cartLayout)
        refresh = view.findViewById(R.id.refreshCart)
        val buy : Button = view.findViewById(R.id.buyButton)
        val reserve : Button = view.findViewById(R.id.resevreButton)

        refresh = view.findViewById(R.id.refreshCart)

        view.post(Thread{
            productsList = Cart.getProducts()
            countList = Cart.getCount()
        })

        buy.setOnClickListener {
            animateInOut(buy)
        }

        reserve.setOnClickListener {
            animateInOut(reserve)
        }

        refresh.setOnRefreshListener{
            refreshItems()
            refresh.isRefreshing = false
        }

        return view
    }

    private fun refreshItems()
    {
        productsList = Cart.getProducts()
        countList = Cart.getCount()
        list.removeAllViews()
        for(product in productsList)
        {
            val newText = BrowserItem(context)
            newText.setText(product.nazwa, product.producent, product.cena, countList[productsList.indexOf(product)])
            newText.setOnClickListener {
                animateInOut(newText)
                context?.let { it1 -> Cart.deleteProduct(product, it1) }
                refreshItems()
            }
            list.addView(newText)
        }
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
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}