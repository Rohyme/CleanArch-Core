package com.tripl3dev.presentation.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.tripl3dev.presentation.R
import com.tripl3dev.presentation.ui.moviesScreen.MoviesFragment
import com.tripl3dev.presentation.ui.peopleScreen.PeopleFragment
import com.tripl3dev.presentation.ui.tvShowsScreen.TvShowsFragment
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.toolbar_layout.toolbar


class MainScreenActivity : AppCompatActivity() {
    val MOVIES_SCREEN = "MOVIES_SCREEN"
    val PEOPLE_SCREEN = "PEOPLE_SCREEN"
    val TVSHOWS_SCREEN = "TVSHOWS_SCREEN"
    var currentScreen: String = MOVIES_SCREEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                currentScreen = savedInstanceState.getString(CURRENT_SCREEN) ?: PEOPLE_SCREEN
            }
        }
        setContentView(R.layout.activity_main_screen)
        setSupportActionBar(toolbar)
        setUpNavigationView(nvView)
        selectDrawerItem(nvView.menu.getItem(getNavigationIndex(currentScreen)))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }


    fun getNavigationIndex(screen: String): Int {
        return when (screen) {
            MOVIES_SCREEN -> {
                0
            }
            TVSHOWS_SCREEN -> {
                1
            }
            PEOPLE_SCREEN -> {
                2
            }
            else -> {
                0
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(Gravity.START)
                return true
            }
            R.id.filterMovies -> {
                return false
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


    val CURRENT_SCREEN = "CURRENT_SCREEN"
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putString(CURRENT_SCREEN, currentScreen)
        }
        super.onSaveInstanceState(outState)
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.nav_movies -> {
                setUpFragment(MOVIES_SCREEN)
                currentScreen = MOVIES_SCREEN
            }
            R.id.nav_people -> {
                setUpFragment(PEOPLE_SCREEN)
                currentScreen = PEOPLE_SCREEN
            }
            R.id.nav_tv -> {
                setUpFragment(TVSHOWS_SCREEN)
                currentScreen = TVSHOWS_SCREEN
            }
            else -> {
                setUpFragment(MOVIES_SCREEN)
                currentScreen = MOVIES_SCREEN
            }
        }
        menuItem.isChecked = true
        title = menuItem.title
        drawer_layout.closeDrawers()
    }

    private fun setUpFragment(tag: String) {
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (tag) {
            MOVIES_SCREEN -> {
                MoviesFragment()
            }
            PEOPLE_SCREEN -> {
                PeopleFragment()
            }
            TVSHOWS_SCREEN -> {
                TvShowsFragment()
            }
            else -> {
                MoviesFragment()
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.mainScreenContent, fragment as Fragment?, tag).commit()

    }


}
