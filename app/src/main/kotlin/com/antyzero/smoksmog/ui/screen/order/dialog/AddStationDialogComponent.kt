package com.antyzero.smoksmog.ui.screen.order.dialog


import com.antyzero.smoksmog.ui.screen.SupportFragmentModule

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(SupportFragmentModule::class))
interface AddStationDialogComponent {

    fun inject(addStationDialog: AddStationDialog)
}
