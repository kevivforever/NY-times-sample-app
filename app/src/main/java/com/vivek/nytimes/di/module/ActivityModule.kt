package com.yummic.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.vivek.nytimes.data.repo.StoryRepository
import com.vivek.nytimes.ui.list.ListViewModel
import com.vivek.nytimes.utils.ViewModelProviderFactory
import com.vivek.nytimes.utils.network.NetworkHelper
import com.yummic.di.ActivityContext
import com.yummic.di.ActivityScope
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    fun provideListViewModel(networkHelper: NetworkHelper,
                             storyRepository: StoryRepository,
                             compositeDisposable: CompositeDisposable
    ) : ListViewModel = ViewModelProviders.of(activity, ViewModelProviderFactory(ListViewModel::class) {
        ListViewModel(networkHelper, storyRepository, compositeDisposable)
    })
        .get(ListViewModel::class.java)

}

