package com.yummic.di.component

import com.vivek.nytimes.ui.MainActivity
import com.yummic.di.ActivityScope
import com.yummic.di.module.ActivityModule
import dagger.Component


@ActivityScope
@Component(
        dependencies = [ApplicationComponent::class],
        modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: MainActivity)

}

