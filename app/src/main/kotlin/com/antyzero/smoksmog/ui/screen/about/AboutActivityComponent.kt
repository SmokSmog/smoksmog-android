package com.antyzero.smoksmog.ui.screen.about

import com.antyzero.smoksmog.ui.screen.ActivityModule

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ActivityModule::class))
interface AboutActivityComponent {
    fun inject(activity: AboutActivity)
}
