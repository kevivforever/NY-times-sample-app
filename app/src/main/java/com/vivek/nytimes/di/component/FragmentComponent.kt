package com.yummic.di.component

import com.vivek.nytimes.ui.detail.DetailFragment
import com.vivek.nytimes.ui.list.ListFragment
import com.yummic.di.FragmentScope
import com.yummic.di.module.ActivityModule
import com.yummic.di.module.FragmentModule
import dagger.Component


@FragmentScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [FragmentModule::class, ActivityModule::class]
)
interface FragmentComponent {

    fun inject(fragment: ListFragment)

    fun inject(fragment: DetailFragment)

}

