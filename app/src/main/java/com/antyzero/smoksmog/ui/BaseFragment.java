package com.antyzero.smoksmog.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;

import com.trello.rxlifecycle.components.RxFragment;

import butterknife.ButterKnife;

/**
 * Base for all fragments
 */
public abstract class BaseFragment extends RxFragment {

    @Override
    @CallSuper
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
        ButterKnife.bind( this, view );
    }
}
