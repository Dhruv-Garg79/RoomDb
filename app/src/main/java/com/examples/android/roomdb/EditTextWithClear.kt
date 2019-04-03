package com.examples.android.roomdb

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.AppCompatEditText
import android.text.TextWatcher
import android.util.AttributeSet
import android.text.Editable
import android.view.MotionEvent
import android.view.View


class EditTextWithClear(context: Context?) : AppCompatEditText(context) {

    var mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_close_grey_24dp, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.isEmpty()){
                    hideClearButton()
                }else showClearButton()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        setOnTouchListener { view, event ->
            var ans = false
            if (compoundDrawablesRelative[2] != null){
                val isClearButtonClicked = when(layoutDirection){
                    View.LAYOUT_DIRECTION_RTL -> {
                        val clearButtonEnd = (mClearButtonImage?.intrinsicWidth ?: - paddingStart) + paddingStart
                        event.x < clearButtonEnd
                    }
                    View.LAYOUT_DIRECTION_LTR -> {
                        val clearButtonStart = width - (mClearButtonImage?.intrinsicWidth ?: - paddingEnd) - paddingEnd
                        event.x > clearButtonStart
                    }
                    else -> false
                }
                if (isClearButtonClicked) {
                    // Check for ACTION_DOWN (always occurs before ACTION_UP).
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        // Switch to the black version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_close_black_24dp, null)
                        showClearButton()
                    }
                    // Check for ACTION_UP.
                    if (event.action == MotionEvent.ACTION_UP) {
                        // Switch to the opaque version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_close_grey_24dp, null)
                        // Clear the text and hide the clear button.
                        this.text?.clear()
                        hideClearButton()
                        ans = true
                    }
                } else {
                    ans = false
                }
            }
            ans
        }
    }

    private fun showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null )
    }
    private fun hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null )
    }
}