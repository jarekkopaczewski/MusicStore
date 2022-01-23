package com.example.musicstore.service

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.musicstore.R

class AnimateView {
    companion object {
        fun animateInOut(button: View, context: Context?) {
            val zoomIn: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomin)
            val zoomOut: Animation = AnimationUtils.loadAnimation(context, R.anim.zoomout)
            button.startAnimation(zoomIn)
            button.startAnimation(zoomOut)
        }
    }
}