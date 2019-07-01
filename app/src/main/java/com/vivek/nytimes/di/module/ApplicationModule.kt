package com.yummic.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vivek.nytimes.TimesApplication
import com.vivek.nytimes.data.local.DatabaseService
import com.vivek.nytimes.data.remote.NetworkService
import com.vivek.nytimes.data.remote.Networking
import com.vivek.nytimes.utils.network.NetworkHelper
import com.yummic.di.ApplicationContext
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: TimesApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService =
            Room.databaseBuilder( application, DatabaseService::class.java,"nytimes" )
                    .build()

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
            Networking.create()

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

}