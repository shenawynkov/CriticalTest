package com.shenawynkov.errorhandler

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData

class MyTextWatcher(
    val error: MutableLiveData<String>,
    val validator: (s: String) -> String?,
    val form: () -> Unit
) : TextWatcher {
    lateinit var handler: Handler

    init {

        handler = Handler()
    }

    val late = 1500L
    var lastString: String = ""
    val runnable = Runnable {
        if (lastString.length > 0)
            error.value = validator(lastString)
        else
            error.value = null

        form()

    }

    override fun afterTextChanged(s: Editable?) {
        lastString = s.toString()

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        error.value = null

        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, late)

    }
}