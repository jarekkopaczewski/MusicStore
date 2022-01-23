package com.example.musicstore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.musicstore.R
import com.example.musicstore.adapters.BannerAdapter
import com.example.musicstore.customView.CategoryItem
import com.example.musicstore.service.AnimateView
import com.example.musicstore.service.DataBaseSupport
import java.util.*
import kotlin.collections.ArrayList

class ProductsFragment : Fragment() {
    private val images : IntArray = intArrayOf(R.drawable.icon, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4)
    private val productImages : IntArray = intArrayOf(R.drawable.bass, R.drawable.perkusja, R.drawable.piano, R.drawable.mikrofon, R.drawable.ukulele)
    private val categoryImages : ArrayList<View> = arrayListOf()
    private var categories : ArrayList<String> = arrayListOf()
    private lateinit var linearOne : LinearLayout
    private lateinit var linearTwo : LinearLayout

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
                activity?.runOnUiThread{
                    if(banner.currentItem != images.size-1 )
                        banner.arrowScroll(View.FOCUS_RIGHT)
                    else
                        banner.setCurrentItem(0,true)
                }
            }
        }, 500, 10000)

        view.post{
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
                    AnimateView.animateInOut(image,context)
                    val fragment = BrowseFragment()
                    println((image as CategoryItem).getName())
                    fragment.setCategory( image.getName() )
                    activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace((view.parent as ViewGroup).id, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
        return view
    }

    fun getSystemService(): Any {
        return this.layoutInflater
    }

    override fun onDestroyView(){
        super.onDestroyView()
        linearOne.removeAllViews()
        linearTwo.removeAllViews()
    }
}