package com.example.memeskotlin.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.memeskotlin.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    var top_down: Animation? = null
    var down_top:Animation? = null
    var imageView: ImageView? = null
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        top_down = AnimationUtils.loadAnimation(this, R.anim.top_down)
        down_top = AnimationUtils.loadAnimation(this, R.anim.down_top)
        imageView = splash_img
        textView = splash_text
        imageView?.animation = top_down
        textView?.animation = down_top
        val secondsDelayed = 3000
        Handler().postDelayed({
            val intent = Intent(
                this, MainActivity::class.java
            )
            startActivity(intent)
            finishAffinity()
        }, secondsDelayed.toLong())
    }

}
