package com.capstone.posturku.ui.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.posturku.R

class EmailEditTextCustom : AppCompatEditText {
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
                val email = p0.toString().trim()

                if (email.isEmpty() || !isEmailValid(email)) {
                    val error = context.getString(R.string.emailerror)
                    afterTextChangedCallback?.onFailure(error)
                }
                else{
                    afterTextChangedCallback?.onSuccess()
                }
            }

            override fun afterTextChanged(s: Editable) {


            }
        })
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun setAfterTextChangedCallback(callback: AfterTextChangedCallback) {
        afterTextChangedCallback = callback
    }

    interface AfterTextChangedCallback {
        fun onSuccess()
        fun onFailure(errorMessage: String)
    }
}