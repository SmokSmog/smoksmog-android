package com.antyzero.smoksmog.ui

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

import com.antyzero.smoksmog.R
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOnCreate(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        ButterKnife.bind(this)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        ButterKnife.bind(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    companion object {

        /**
         * Shared initialization among activities, use it you cannot extend BaseActivity

         * @param activity for access to various data
         */
        fun initOnCreate(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val color: Int
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    color = activity.resources.getColor(R.color.primary, activity.theme)
                } else {

                    color = activity.resources.getColor(R.color.primary)
                }
                activity.window.navigationBarColor = color
            }
        }
    }
}
