package com.capstone.posturku.ui.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.posturku.R

class PassEditTextCustom : AppCompatEditText {
    private var afterTextChangedCallback: AfterTextChangedCallback? = null


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && p0.length < 8) {
                    val error = context.getString(R.string.passError)
                    afterTextChangedCallback?.onFailure(error)
                } else {
                    afterTextChangedCallback?.onSuccess()
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    fun setAfterTextChangedCallback(callback: AfterTextChangedCallback) {
        afterTextChangedCallback = callback
    }

    interface AfterTextChangedCallback {
        fun onSuccess()
        fun onFailure(errorMessage: String)
    }
}