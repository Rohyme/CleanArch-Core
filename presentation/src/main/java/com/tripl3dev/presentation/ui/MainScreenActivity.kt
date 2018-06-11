package com.tripl3dev.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.base.BaseFragmentWithInjector
import com.tripl3dev.presentation.ui.moviesScreen.MoviesFragment
import com.tripl3dev.presentation.ui.peopleScreen.PeopleScreen
import com.tripl3dev.presentation.ui.tvShowsScreen.TvShowsScreen
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar


class MainScreenActivity : AppCompatActivity() {


    companion object {
        fun navigateToMainScreen(context: Context) {
            val intent = Intent(context, MainScreenActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        setSupportActionBar(toolbar)
        setUpNavigationView(nvView)
        selectDrawerItem(nvView.menu.getItem(0))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        drawer_layout.openDrawer(Gravity.START)
        when (item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(Gravity.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setUpNavigationView(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener {
            selectDrawerItem(it)
            return@setNavigationItemSelectedListener true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {

        var fragment: BaseFragmentWithInjector? = null

        val fragmentClass: Class<*> = when (menuItem.itemId) {
            R.id.nav_movies ->
                MoviesFragment::class.java
            R.id.nav_people ->
                PeopleScreen::class.java
            R.id.nav_tv ->
                TvShowsScreen::class.java
            else ->
                MoviesFragment::class.java
        }



        try {
            fragment = fragmentClass.newInstance() as BaseFragmentWithInjector
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.mainScreenContent, fragment).commit()
        menuItem.isChecked = true
        title = menuItem.title
        drawer_layout.closeDrawers()
    }


}
