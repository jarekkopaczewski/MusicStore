package com.example.musicstore

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BrowseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var product: ProductData
    private lateinit var shopProductSM: ProductSM
    private var type : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    fun setProduct( product : ProductData ){
        this.product = product
        this.type = 0
    }

    fun setProduct( product : ProductSM ){
        this.shopProductSM = product
        this.type = 1
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.item_site, container, false)
        val itemName : TextView = view.findViewById(R.id.itemName)
        val itemCompany : TextView = view.findViewById(R.id.itemCompany)
        val itemPrice : TextView = view.findViewById(R.id.itemPrice)
        val orderButton : ImageView = view.findViewById(R.id.addToCartButton)

        if( type == 1)
        {
            itemName.text = shopProductSM.nazwa
            itemCompany.text = shopProductSM.kod_kreskowy
            itemPrice.text = shopProductSM.ilosc.toString()
        }
        else
        {
            itemName.text = product.nazwa
            itemCompany.text = product.producent
            itemPrice.text = product.cena.toString()
        }

        orderButton.setOnClickListener{
            animateInOut(orderButton)
            context?.let { it1 -> Cart.addProduct(product, it1) }
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BrowseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}