package com.tripl3dev.presentation.ui.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseActivityWithInjector
import com.tripl3dev.presentation.base.BaseViewModel
import com.tripl3dev.presentation.ui.MainScreenActivity
import com.tripl3dev.prettystates.StatesConfigFactory
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import kotlinx.android.synthetic.main.activity_guest_session_login.*

class GuestSessionLogin : BaseActivityWithInjector() {

    override fun getActivityVM(): Class<out BaseViewModel> {
        return GuestSessionVM::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_session_login)
        val viewModel = vm as GuestSessionVM
        viewModel.checkUserLoggedIn()
        StatesConfigFactory.get().addStateView(Constants.BUTTON_ERROR_VIEW, R.layout.button_error_layout)

        loginBt.setOnClickListener {
            viewModel.loginAsGuest()
        }


        viewModel.getLoginObserve().observe(this, Observer<Stateview> { t ->
            when (t) {
                is Stateview.Success<*> -> {
                    loginBt.setState(StatesConstants.NORMAL_STATE)
                    val intent = Intent(this@GuestSessionLogin, MainScreenActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Guest has login successfully", Toast.LENGTH_SHORT).show()
                }
                is Stateview.Loading -> {
                    loginBt.setState(StatesConstants.LOADING_STATE)
                }
                is Stateview.Failure -> {
                    loginBt.setState(Constants.BUTTON_ERROR_VIEW).findViewById<ImageButton>(R.id.errorViewButton).setOnClickListener {
                        viewModel.createUserUseCase.retry()
                    }
                    Toast.makeText(this, t.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.isLoggedIn().observe(this, Observer<Boolean> {
            if (it!!) {
                val intent = Intent(this@GuestSessionLogin, MainScreenActivity::class.java)
                startActivity(intent)
            }
        })


    }
}
