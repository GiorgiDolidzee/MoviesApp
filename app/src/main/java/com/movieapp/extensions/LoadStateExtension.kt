package com.movieapp.extensions

import android.util.Log.d
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.getErrorMessage() : String {
    d("JEMALI", this.append.toString())
    val errorState = when {
        this.append is LoadState.Error -> this.append as LoadState.Error
        this.prepend is LoadState.Error -> this.prepend as LoadState.Error
        this.refresh is LoadState.Error -> this.refresh as LoadState.Error
        else -> null
    }
    return errorState?.error?.message.toString()
}