package com.example.musicstore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val images : IntArray = intArrayOf(R.drawable.icon, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4)
    private val productImages : IntArray = intArrayOf(R.drawable.bass, R.drawable.perkusja, R.drawable.piano, R.drawable.mikrofon, R.drawable.ukulele)
    private val categoryImages : ArrayList<View> = arrayListOf()
    private var categories : ArrayList<String> = arrayListOf()
    private lateinit var linearOne : LinearLayout
    private lateinit var linearTwo : LinearLayout

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
    ): View {

        val view :View =  inflater.inflate(R.layout.fragment_products, container, false)
        val banner : ViewPager = view.findViewById(R.id.banner_pager)
        linearOne = view.findViewById(R.id.products_row_one)
        linearTwo = view.findViewById(R.id.products_row_two)
        categories = DataBaseSupport.getCategories()

        val mViewPagerAdapter : PagerAdapter = BannerAdapter(this, images)
        banner.adapter = mViewPagerAdapter

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread(Runnable {
                    if(banner.currentItem != images.size-1 )
                        banner.arrowScroll(View.FOCUS_RIGHT)
                    else
                        banner.setCurrentItem(0,true)
                })
            }
        }, 500, 10000)

        view.post(Runnable {
            var side = false
            for( i in 0 until categories.size)
            {
                if(side)
                {
                    val temp = CategoryItem(context, productImages[i])
                    temp.setName(categories[i])
                    categoryImages.add(temp)
                    linearTwo.addView(categoryImages[i])
                    side = false
                }
                else
                {
                    val temp = CategoryItem(context, productImages[i])
                    temp.setName(categories[i])
                    categoryImages.add(temp)
                    linearOne.addView(categoryImages[i])
                    side = true
                }
            }

            for(image in categoryImages)
            {
                image.setOnClickListener {
                    animateInOut(image)
                    val fragment = BrowseFragment()
                    println((image as CategoryItem).getName())
                    fragment.setCategory( (image as CategoryItem).getName() )
                    activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace((view!!.parent as ViewGroup).id, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        })

        return view
    }

    fun refreshProducts()
    {

    }

    fun getSystemService(layoutInflaterService: String): Any {
        return this.layoutInflater
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun animateInOut(button: View)
    {
        val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
        val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
        button.startAnimation(zoomIn)
        button.startAnimation(zoomOut)
    }

    override fun onDestroyView(){
        super.onDestroyView()
        linearOne.removeAllViews()
        linearTwo.removeAllViews()
    }

    override fun onResume()
    {
        super.onResume()

    }
}