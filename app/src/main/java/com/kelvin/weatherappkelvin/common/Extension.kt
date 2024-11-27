package com.kelvin.weatherappkelvin.common

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

 fun EditText.showKeyboardOnFocus(context: Context) {
    setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

suspend fun <T> LiveData<T>.awaitValue(): T {
    return withContext(Dispatchers.Main) {
        suspendCancellableCoroutine { continuation ->
            val observer = object : Observer<T> {
                override fun onChanged(value: T) {
                    if (value != null) {
                        continuation.resume(value) {}
                        this@awaitValue.removeObserver(this)
                    }
                }

            }
            observeForever(observer)
            continuation.invokeOnCancellation { removeObserver(observer) }
        }
    }
}