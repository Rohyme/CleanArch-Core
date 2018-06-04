package com.tripl3dev.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tripl3dev.domain.service.ApiService
import com.tripl3dev.presentation.application.MyApplication
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
   @Inject
   lateinit var  apiService : ApiService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).networkComponent.inject(this)

    }
}
