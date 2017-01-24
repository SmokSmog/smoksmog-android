package com.antyzero.smoksmog.ui

import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.dsl.fullscreen
import com.antyzero.smoksmog.dsl.getCompatColor
import com.antyzero.smoksmog.dsl.navBarHeight
import com.antyzero.smoksmog.dsl.statusBarHeight
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Base Activity that contains Dragon image and pollution background
 */
abstract class BaseDragonActivity : RxAppCompatActivity() {

    lateinit private var imageViewDragon: ImageView
    lateinit protected var container: ViewGroup
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)

        imageViewDragon = findViewById(R.id.imageViewDragon) as ImageView
        container = findViewById(R.id.container) as ViewGroup

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && addExtraTopPadding()) {
            fullscreen()
            container.setPadding(0, statusBarHeight(), 0, 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (resources.configuration.orientation == ORIENTATION_PORTRAIT) {
                window.navigationBarColor = getCompatColor(android.R.color.transparent)
            }
        }

        setupDragon()
    }

    override fun setContentView(layoutResID: Int) {
        LayoutInflater.from(this).inflate(layoutResID,container,true)
    }

    protected fun addExtraTopPadding(): Boolean = true

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    /**
     * Setup Dragon image position
     */
    private fun setupDragon() {
        val layoutParams = RelativeLayout.LayoutParams(imageViewDragon.layoutParams as ViewGroup.MarginLayoutParams)
        layoutParams.bottomMargin = navBarHeight(R.dimen.dragon_margin_bottom_default)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        imageViewDragon.layoutParams = layoutParams
        imageViewDragon.visibility = View.VISIBLE
    }
}
