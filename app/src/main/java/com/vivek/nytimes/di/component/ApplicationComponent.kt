package com.yummic.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.vivek.nytimes.TimesApplication
import com.vivek.nytimes.data.local.DatabaseService
import com.vivek.nytimes.data.remote.NetworkService
import com.vivek.nytimes.data.repo.StoryRepository
import com.vivek.nytimes.utils.network.NetworkHelper
import com.yummic.di.ApplicationContext
import com.yummic.di.module.ApplicationModule
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: TimesApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getNetworkHelper(): NetworkHelper

    fun getCompositeDisposable(): CompositeDisposable

    fun getStoryRespository(): StoryRepository
}