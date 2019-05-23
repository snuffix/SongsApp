package com.github.snuffix.songapp.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

inline fun Context.inflateView(@LayoutRes layoutRes: Int, parent: ViewGroup?, attachToRoot: Boolean = false) = LayoutInflater.from(this).inflate(layoutRes, parent, attachToRoot)