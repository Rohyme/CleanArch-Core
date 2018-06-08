package com.tripl3dev.presentation.di.modules.sharedPreferenceDi

import android.content.Context
import android.content.SharedPreferences
import com.tripl3dev.dataStore.SharedPreferenceUtil
import com.tripl3dev.domain.managers.Constants
import com.tripl3dev.presentation.application.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreference(app: MyApplication): SharedPreferences {
        return app.getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceUtil(sharedPreference: SharedPreferences): SharedPreferenceUtil {
        return SharedPreferenceUtil(sharedPreference)
    }

}