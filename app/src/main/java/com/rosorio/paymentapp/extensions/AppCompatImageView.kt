package com.rosorio.paymentapp.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadImage(url: String) {
    Glide.with(this.context).asGif().load(url).into(this)
}