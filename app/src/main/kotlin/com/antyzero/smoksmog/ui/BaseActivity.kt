package com.antyzero.smoksmog.ui

import android.app.Activity
import android.content.Context
import android.os.Build
import com.antyzero.smoksmog.R
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity : RxAppCompatActivity() {

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
