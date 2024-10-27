package com.zerozealed.eleven

import androidx.lifecycle.LiveData


fun <E> LiveData<E>.requireValue(): E =
    this.value ?: throw IllegalStateException("Value in LiveData is null")
