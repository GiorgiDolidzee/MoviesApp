package com.movieapp.utils.extensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.getErrorMessage() : String {
    val errorState = when {
        this.append is LoadState.Error -> this.append as LoadState.Error
        this.prepend is LoadState.Error -> this.prepend as LoadState.Error
        this.refresh is LoadState.Error -> this.refresh as LoadState.Error
        else -> null
    }
    return errorState?.error?.message.toString()
}