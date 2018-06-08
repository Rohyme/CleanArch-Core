package com.tripl3dev.presentation.di.appDi

import android.content.Context
import com.tripl3dev.presentation.application.MyApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    @ForApplication
    fun applicationContext(app: MyApplication): Context {
        return app.applicationContext
    }

}