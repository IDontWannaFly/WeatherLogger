package com.mark.weatherlogger.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mark.weatherlogger.R

@BindingAdapter("android:src")
fun setImageLinkSource(view: ImageView, src: String?){
    src?.let{
        Glide.with(view).load(it).placeholder(R.drawable.image_placeholder).into(view)
    }
}