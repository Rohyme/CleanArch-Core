package com.tripl3dev.presentation.di.appDi

import android.content.Context
import com.tripl3dev.presentation.application.MyApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val app: MyApplication) {

    @Provides
    @ForApplication
    fun applicationContext(): Context {
        return app.applicationContext
    }

}