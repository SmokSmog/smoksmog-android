package com.antyzero.smoksmog.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import com.trello.rxlifecycle.components.RxFragment

abstract class BaseFragment : RxFragment() {

    @CallSuper
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
