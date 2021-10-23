package com.shenawynkov.criticaltest.utils


import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shenawynkov.criticaltest.R
import com.shenawynkov.criticaltest.base.GenericAdapter

object BindingAdapters {


    @BindingAdapter("loadImageCenter")
    @JvmStatic
    fun loadImageCenter(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
                .load(imageUrl).placeholder(R.drawable.placeholder).centerInside()
                .into(view)
    }
    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
                .load(imageUrl).placeholder(R.drawable.placeholder).centerCrop()
                .into(view)
    }
    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: Int?) {
        Glide.with(view.context)
                .load(imageUrl).placeholder(R.drawable.placeholder)
                .into(view)
    }


}