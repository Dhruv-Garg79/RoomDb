package com.examples.android.roomdb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_word.*

class NewWordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        save.setOnClickListener {
            if (editText.text.isNullOrEmpty()) {
                Toast.makeText(this@NewWordActivity, "Empty field", Toast.LENGTH_SHORT).show()
            } else {
                val word = editText.text.toString()
                val intent = Intent().putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "REPLY"
    }
}
