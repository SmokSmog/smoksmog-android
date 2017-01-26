package com.antyzero.smoksmog.ui.screen

import com.antyzero.smoksmog.google.GoogleModule
import com.antyzero.smoksmog.ui.screen.about.AboutActivity
import com.antyzero.smoksmog.ui.screen.history.HistoryActivity
import com.antyzero.smoksmog.ui.screen.order.OrderActivity
import com.antyzero.smoksmog.ui.screen.start.StartActivity
import com.antyzero.smoksmog.ui.screen.start.fragment.LocationStationFragmentComponent
import com.antyzero.smoksmog.ui.widget.StationWidgetConfigureActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    operator fun plus(fragmentModule: FragmentModule): FragmentComponent

    fun plus(fragmentModule: FragmentModule, googleModule: GoogleModule): LocationStationFragmentComponent

    fun inject(activity: HistoryActivity)

    fun inject(activity: AboutActivity)

    fun inject(startActivity: StartActivity)

    fun inject(orderActivity: OrderActivity)

    fun inject(pickStationActivity: PickStationActivity)

    fun inject(stationWidgetConfigureActivity: StationWidgetConfigureActivity)
}
