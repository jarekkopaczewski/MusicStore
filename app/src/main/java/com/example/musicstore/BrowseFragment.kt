package com.example.musicstore

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BrowseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BrowseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var productsList : ArrayList<ProductData> = arrayListOf()
    private var category : String = ""
    private var searchConstrain : String = ""

    fun setCategory( category : String)
    {
        this.category = category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_browse, container, false)
        val list : LinearLayout = view.findViewById(R.id.list)
        val refresh : SwipeRefreshLayout = view.findViewById(R.id.refreshLayout)
        val browser : EditText = view.findViewById(R.id.browseConstrain)

        refresh.setOnRefreshListener {
            list.removeAllViews()
            for(produkt in productsList)
            {
                if( produkt.producent.contains(searchConstrain) || produkt.nazwa.contains(searchConstrain) || searchConstrain == "")
                {
                    val newText = BrowserItem(context)
                    newText.setText(produkt.nazwa, produkt.producent, produkt.cena)
                    newText.setOnClickListener {
                        animateInOut(newText)

                        val fragment = ItemFragment()
                        fragment.setProduct( produkt )
                        activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace((view!!.parent as ViewGroup).id, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                    list.addView(newText)
                }
            }
            refresh.isRefreshing = false
        }

        view.post(Thread{
            context?.let { it1 -> DataBaseSupport.getProductsFromBase(it1, category) }
            productsList = DataBaseSupport.getProductsData()
            browser.hint = "Szukaj w ${category.decapitalize()}"
        })

        browser.doOnTextChanged { text, start, before, count ->
            searchConstrain = browser.text.toString()
            list.removeAllViews()
            for(produkt in productsList)
            {
                if( produkt.producent.toLowerCase().contains(searchConstrain.toLowerCase()) ||
                    produkt.nazwa.toLowerCase().contains(searchConstrain.toLowerCase()) || searchConstrain == "")
                {
                    val newText = BrowserItem(context)
                    newText.setText(produkt.nazwa, produkt.producent, produkt.cena)

                    newText.setOnClickListener {
                        animateInOut(newText)

                        val fragment = ItemFragment()
                        fragment.setProduct( produkt )
                        activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace((view!!.parent as ViewGroup).id, fragment)
                            .addToBackStack(null)
                            .commit()
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
         * @return A new instance of fragment BrowseFragment.
         */
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