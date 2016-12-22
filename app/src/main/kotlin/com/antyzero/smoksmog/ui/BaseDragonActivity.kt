package com.antyzero.smoksmog.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout

import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.ui.utils.DimenUtils
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

import butterknife.ButterKnife
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

import android.content.res.Configuration.ORIENTATION_PORTRAIT

/**
 * Base Activity that contains Dragon image and pollution background
 */
abstract class BaseDragonActivity : RxAppCompatActivity() {

    private var imageViewDragon: ImageView? = null
    protected var container: ViewGroup? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)

        imageViewDragon = findViewById(R.id.imageViewDragon) as ImageView
        container = findViewById(R.id.container) as ViewGroup

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && addExtraTopPadding()) {
            container!!.setPadding(0, DimenUtils.getStatusBarHeight(this), 0, 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val color: Int
            val colorResourceId = android.R.color.transparent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = resources.getColor(colorResourceId, theme)
            } else {
                color = resources.getColor(colorResourceId)
            }
            if (resources.configuration.orientation == ORIENTATION_PORTRAIT) {
                window.navigationBarColor = color
            }
        }

        setupDragon()
    }

    /**
     * Override if needed, this solve issue with 4.4 status bar

     * @return
     */
    protected fun addExtraTopPadding(): Boolean {
        return true
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        LayoutInflater.from(this).inflate(layoutResID, container, true)
        ButterKnife.bind(this)
    }

    override fun setContentView(view: View) {
        container!!.addView(view)
        ButterKnife.bind(this)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        container!!.addView(view, params)
        ButterKnife.bind(this)
    }

    /**
     * Setup Dragon image position
     */
    private fun setupDragon() {
        val layoutParams = RelativeLayout.LayoutParams(imageViewDragon!!.layoutParams as ViewGroup.MarginLayoutParams)
        layoutParams.bottomMargin = DimenUtils.getNavBarHeight(this, R.dimen.dragon_margin_bottom_default)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        imageViewDragon!!.layoutParams = layoutParams
        imageViewDragon!!.visibility = View.VISIBLE
    }
}
