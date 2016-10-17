package com.antyzero.smoksmog.ui.screen.start;

import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.antyzero.smoksmog.ui.screen.start.fragment.StationFragment;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Adapter for sliding pages left-right
 */
public class StationSlideAdapter extends FragmentStatePagerAdapter {

    private final List<Long> stationIds;

    private SparseArray<WeakReference<StationFragment>> fragmentRegister = new SparseArray<>();

    /**
     * Standard constructor
     *
     * @param fragmentManager for fragment manipulations
     * @param stationIds      list of IDs for data download
     */
    public StationSlideAdapter(FragmentManager fragmentManager, List<Long> stationIds) {
        super(fragmentManager);
        this.stationIds = stationIds;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        StationFragment fragment = (StationFragment) super.instantiateItem(container, position);
        fragmentRegister.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentRegister.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public StationFragment getItem(int position) {
        return StationFragment.newInstance(stationIds.get(position));
    }

    public WeakReference<StationFragment> getFragmentReference(int position) {
        return fragmentRegister.get(position);
    }

    @Override
    public int getCount() {
        return stationIds.size();
    }
}
