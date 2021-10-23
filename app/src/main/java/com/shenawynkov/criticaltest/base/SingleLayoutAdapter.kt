package com.shenawynkov.criticaltest.base

import androidx.lifecycle.LifecycleOwner


abstract class SingleLayoutAdapter( genericClickListener:(item:Any)->Unit ,private val layoutId: Int) : MyBaseAdapter(genericClickListener) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }
}
