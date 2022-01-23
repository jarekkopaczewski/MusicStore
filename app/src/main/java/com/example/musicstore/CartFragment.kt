package com.example.musicstore

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        if(LoginInterface.getStatus() && LoginInterface.getType() == Type.K)
        {
            buy.isEnabled = true
            reserve.isEnabled = true
        }
        else
        {
            buy.isEnabled = false
            reserve.isEnabled = false
        }

        refresh = view.findViewById(R.id.refreshCart)

        view.post(Thread{
            productsList = Cart.getProducts()
            countList = Cart.getCount()
        })

        buy.setOnClickListener {
            animateInOut(buy)
            if( Cart.getProducts().size == 0)
            {
                Toast.makeText(context, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if(LoginInterface.getAddressState())
                {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setTitle("Confirm")
                    builder.setMessage("Are you sure?")
                    builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                        context?.let { it1 -> DataBaseSupport.addOrder(it1, 3) }
                        calcCart(4, context)
                        Toast.makeText(context, "You will get email with confirm", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    })
                    builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
                    val alert: AlertDialog = builder.create()
                    alert.show()
                }
                else
                {
                    Toast.makeText(context, "Your address is missing", Toast.LENGTH_SHORT).show()
                }
            }
        }

        reserve.setOnClickListener {
            animateInOut(reserve)
            if( Cart.getProducts().size == 0)
            {
                Toast.makeText(context, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Confirm")
                builder.setMessage("Are you sure?")
                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                    context?.let { it1 -> DataBaseSupport.addOrder(it1, 4) }
                    calcCart(4, context)
                    Toast.makeText(context, "You will get email with confirm", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                })
                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
                val alert: AlertDialog = builder.create()
                alert.show()
            }
        }

        refresh.setOnRefreshListener{
            refreshItems()
            refresh.isRefreshing = false
        }

        return view
    }

    fun calcCart(status : Int, con: Context?)
    {
        val productsList : ArrayList<ProductData> = Cart.getProducts()
        val countList : ArrayList<Int> = Cart.getCount()

        for( i in 0 until productsList.size)
        {
            con?.let { it1 -> DataBaseSupport.addOrderData(it1, productsList[i].kod_kreskowy, countList[i], status ) }
        }
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