package com.antyzero.smoksmog.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View

import com.trello.rxlifecycle.components.RxFragment

import butterknife.ButterKnife

/**
 * Base for all fragments
 */
abstract class BaseFragment : RxFragment() {

    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
    }
}
