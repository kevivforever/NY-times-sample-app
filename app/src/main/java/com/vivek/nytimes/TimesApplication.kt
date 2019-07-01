package com.vivek.nytimes

import android.app.Application
import com.yummic.di.component.ApplicationComponent
import com.yummic.di.component.DaggerApplicationComponent
import com.yummic.di.module.ApplicationModule


class TimesApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}