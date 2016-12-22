package com.antyzero.smoksmog.ui.screen

import com.antyzero.smoksmog.ui.screen.settings.GeneralSettingsFragment
import com.antyzero.smoksmog.ui.screen.start.fragment.NetworkStationFragment

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(generalSettingsFragment: GeneralSettingsFragment)

    fun inject(networkStationFragment: NetworkStationFragment)
}
