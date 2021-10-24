package com.shenawynkov.criticaltest.utils


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    view.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    view.visibility = View.VISIBLE

                    return false
                }
            })
            .placeholder(R.drawable.placeholder).centerCrop()
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