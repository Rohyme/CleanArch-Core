package com.tripl3dev.presentation.ui.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tripl3dev.presentation.R

class MainScreen : AppCompatActivity() {


    companion object {
        fun navigateToMainScreen(context: Context) {
            val intent = Intent(context, MainScreen::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
    }
}
