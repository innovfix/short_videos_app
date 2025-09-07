package com.app.reelshort.callbacks

interface OnItemSelectionListener<T> {
    fun onItemSelected(selectedItemDetails: T)
}