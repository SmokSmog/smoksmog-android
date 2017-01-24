package com.antyzero.smoksmog.ui.screen.start

import android.app.FragmentManager
import android.support.v13.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.antyzero.smoksmog.storage.model.Item

import com.antyzero.smoksmog.ui.screen.start.fragment.StationFragment

import java.lang.ref.WeakReference

/**
 * Adapter for sliding pages left-right
 */
class StationSlideAdapter(fragmentManager: FragmentManager, private val stationIds: List<Item>) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentRegister = SparseArray<WeakReference<StationFragment>?>()

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as StationFragment
        fragmentRegister.put(position, WeakReference(fragment))
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        fragmentRegister.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): StationFragment {
        return StationFragment.newInstance(stationIds[position].id)
    }

    fun getFragmentReference(position: Int): WeakReference<StationFragment>? {
        return fragmentRegister.get(position)
    }

    override fun getCount(): Int {
        return stationIds.size
    }
}
