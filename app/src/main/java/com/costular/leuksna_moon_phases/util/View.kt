package com.costular.leuksna_moon_phases.util

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes


fun TypedArray.readRecycling(block: TypedArray.() -> Unit) {
    block()
    recycle()
}

fun View.readAttrColor(@AttrRes attr: Int): Int {
    val typedValue = TypedValue().also {
        context.theme.resolveAttribute(attr, it, true)
    }
    return typedValue.data
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.sp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()