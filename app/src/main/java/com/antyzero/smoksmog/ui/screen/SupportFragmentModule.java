package com.antyzero.smoksmog.ui.screen;

import android.support.v4.app.Fragment;

import dagger.Module;

@Module
public class SupportFragmentModule {

    private final Fragment fragment;

    public SupportFragmentModule( Fragment fragment ) {
        this.fragment = fragment;
    }
}
