package com.example.musicstore

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicstore.R

class CategoryItem(context: Context?, id :Int) : ConstraintLayout(context!!)
{
    private var imageText : TextView
    init {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.category_image, this, true)
        val image : ImageView = view.findViewById(R.id.categoryImage)
        imageText = view.findViewById(R.id.categoryText)
        image.setImageResource(id)
    }

    fun setName( name : String ) {
        imageText.text = name
    }

    fun getName() : String {
        return imageText.text.toString()
    }
}