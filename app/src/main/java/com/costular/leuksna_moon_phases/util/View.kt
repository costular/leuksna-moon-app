package com.costular.leuksna_moon_phases.util

import android.content.res.TypedArray
import android.util.AttributeSet


fun TypedArray.readRecycling(block: TypedArray.() -> Unit) {
    block()
    recycle()
}