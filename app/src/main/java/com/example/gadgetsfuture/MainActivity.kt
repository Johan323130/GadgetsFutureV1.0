package com.example.gadgetsfuture

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, InicioSesion::class.java))
        this@MainActivity.overridePendingTransition(
            R.anim.animate_fade_enter,
            R.anim.animate_fade_exit
        )
    }

}