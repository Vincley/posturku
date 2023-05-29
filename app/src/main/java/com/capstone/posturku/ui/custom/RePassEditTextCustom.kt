package com.capstone.posturku.ui.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.posturku.R

class RePassEditTextCustom : AppCompatEditText {
    private var afterTextChangedCallback: AfterTextChangedCallback? = null
    private var originalPassword: String = ""


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
                val rePassword = p0?.toString() ?: ""
                if (rePassword.isEmpty()) {
                    afterTextChangedCallback?.onEmpty()
                } else if (rePassword != originalPassword) {
                    val errorMessage = context.getString(R.string.password_not_match)
                    afterTextChangedCallback?.onFailure(errorMessage)
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

    fun setOriginalPassword(password: String) {
        originalPassword = password
    }

    interface AfterTextChangedCallback {
        fun onSuccess()
        fun onFailure(errorMessage: String)
        fun onEmpty()
    }
}