package com.tripl3dev.presentation.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tripl3dev.domain.Entity.PostEntity
import com.tripl3dev.domain.managers.Stateview
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.application.MyApplication
import com.tripl3dev.presentation.base.BaseActivity
import com.tripl3dev.presentation.di.modules.viewModelDi.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    var isLoading: LiveData<Boolean> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).networkComponent.inject(this)

        val vm = ViewModelProviders.of(this, factory)[MainActivityViewModel::class.java]
        vm.getShowPostUpdated().observe(this, Observer<Stateview> { t ->
            if (t !is Stateview.Loading) {
                progress.visibility = View.GONE
                testText.visibility = View.VISIBLE
            }
            when (t) {
                is Stateview.Success<*> -> {
                    val data = t.data as ArrayList<PostEntity>
                    Toast.makeText(this@MainActivity, data[0].title, Toast.LENGTH_SHORT).show()
                    testText.text = "First Post title is ${data[0].title}"
                }

                is Stateview.HasNoData -> {
                    Toast.makeText(this@MainActivity, "No data Found ya 3m el 7ag", Toast.LENGTH_SHORT).show()
                }

                is Stateview.Failure -> {
                    Toast.makeText(this@MainActivity, t.error.message, Toast.LENGTH_SHORT).show()
                }
                is Stateview.Loading -> {
                    progress.visibility = View.VISIBLE
                    testText.visibility = View.GONE
                }
            }
        })
    }
}
