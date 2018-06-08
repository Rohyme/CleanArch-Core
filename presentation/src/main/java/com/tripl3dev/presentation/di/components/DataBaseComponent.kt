package com.tripl3dev.presentation.di.components

import com.tripl3dev.presentation.di.appDi.ApplicationModule
import com.tripl3dev.presentation.di.dataBaseDi.DataBaseModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, DataBaseModule::class])
interface DataBaseComponent {
}