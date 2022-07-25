package com.movieapp.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.movieapp.R

fun ImageView.setImage(url: String?) {
    if(!url.isNullOrEmpty()) {
        Glide.with(context).load(url).placeholder(R.color.gray).dontAnimate().error(R.color.gray).into(this)
    } else {
        setImageResource(R.color.gray)
    }
}