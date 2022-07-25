package com.movieapp.utils.extensions

import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, length).show()
}

fun View.visible() {
    this.isVisible = true
}

fun View.hide() {
    this.isVisible = false
}