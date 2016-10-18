package com.antyzero.smoksmog.ui.screen;

import android.app.Fragment;

import dagger.Module;

@Module
public class FragmentModule {

    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }
}
