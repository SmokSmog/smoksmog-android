package com.antyzero.smoksmog.ui.screen.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.compatFromHtml
import com.antyzero.smoksmog.ui.BaseActivity
import com.antyzero.smoksmog.ui.screen.ActivityModule
import kotlinx.android.synthetic.main.dialog_info_about.*
import smoksmog.logger.Logger
import javax.inject.Inject

class AboutActivity : BaseActivity() {

    @Inject lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SmokSmogApplication[this].appComponent.plus(ActivityModule(this)).inject(this)
        setContentView(R.layout.dialog_info_about)

        textView.compatFromHtml(R.string.about)
        textView.movementMethod = LinkMovementMethod.getInstance()

        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            textViewVersionName.text = getString(R.string.version_name_and_code,
                    packageInfo.versionName,
                    packageInfo.versionCode)
        } catch (e: Exception) {
            logger.i(TAG, "Problem with obtaining version", e)
        }

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
