package com.faob.motionlayoutexamples

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SlideMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = intent.getIntExtra("layout_file_id", R.layout.slide_menu_1)
        setContentView(layout)

        val content = findViewById<View>(R.id.menu_content)
        content.cameraDistance = 12000f
    }
}