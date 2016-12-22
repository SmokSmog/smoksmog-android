package com.antyzero.smoksmog.ui.screen.about

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView

import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.ui.BaseActivity
import com.antyzero.smoksmog.ui.screen.ActivityModule

import javax.inject.Inject

import butterknife.Bind
import butterknife.ButterKnife
import smoksmog.logger.Logger

class AboutActivity : BaseActivity() {

    @Inject
    lateinit var logger: Logger

    @Bind(R.id.textView)
    internal var textView: TextView? = null
    @Bind(R.id.textViewVersionName)
    internal var textViewVersionName: TextView? = null
    @Bind(R.id.toolbar)
    internal var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SmokSmogApplication[this].appComponent.plus(ActivityModule(this)).inject(this)
        setContentView(R.layout.dialog_info_about)
        ButterKnife.bind(this)

        textView!!.text = Html.fromHtml(getString(R.string.about))
        textView!!.movementMethod = LinkMovementMethod.getInstance()

        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            textViewVersionName!!.text = getString(R.string.version_name_and_code,
                    packageInfo.versionName,
                    packageInfo.versionCode)
        } catch (e: Exception) {
            logger!!.i(TAG, "Problem with obtaining version", e)
        }

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    companion object {

        private val TAG = "AboutActivity"

        fun start(context: Context) {
            context.startActivity(intent(context))
        }

        fun intent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }
}
