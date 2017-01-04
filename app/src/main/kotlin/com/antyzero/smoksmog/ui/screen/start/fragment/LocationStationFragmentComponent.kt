package com.antyzero.smoksmog.ui.screen.start.fragment


import com.antyzero.smoksmog.google.GoogleModule
import com.antyzero.smoksmog.ui.screen.FragmentModule

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(FragmentModule::class, GoogleModule::class))
interface LocationStationFragmentComponent {

    fun inject(fragment: LocationStationFragment)
}
