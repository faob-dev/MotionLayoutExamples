package com.faob.motionlayoutexamples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = intent.getIntExtra("layout_file_id", R.layout.card_detail)
        setContentView(layout)
    }
}