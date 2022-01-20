package com.example.musicstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.musicstore.ProductsFragment
import com.example.musicstore.R
import java.util.*


class BannerAdapter(private val context: ProductsFragment, private var images: IntArray) : PagerAdapter() {

    private var mLayoutInflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() : Int {
        return images.size;
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`;
    }

    override fun  instantiateItem( container : ViewGroup, position : Int) : Any {
        val itemView : View = mLayoutInflater.inflate(R.layout.banner_image, container, false)
        val imageView : ImageView = itemView.findViewById(R.id.product_image)
        imageView.setImageResource(images[position])
        Objects.requireNonNull(container).addView(itemView)
        return itemView;
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
    {
        container.removeView(`object` as LinearLayout?)
    }
}