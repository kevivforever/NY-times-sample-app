package com.yummic.di.module


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vivek.nytimes.ui.list.ListViewModel
import com.yummic.di.FragmentScope
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    fun provideFragment(): Fragment = fragment

    // this needs activity context
//    @Provides
//    fun provideListViewModel() : ListViewModel = ViewModelProviders.of(fragment)
//            .get(ListViewModel::class.java)
}
