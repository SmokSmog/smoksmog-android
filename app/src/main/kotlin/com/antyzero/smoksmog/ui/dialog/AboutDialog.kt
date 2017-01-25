package com.antyzero.smoksmog.ui.dialog

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.compatFromHtml
import com.antyzero.smoksmog.dsl.tag
import com.antyzero.smoksmog.user.User
import smoksmog.logger.Logger
import javax.inject.Inject

class AboutDialog : InfoDialog() {

    @Inject lateinit var logger: Logger
    @Inject lateinit var user: User
    
    lateinit var textView: TextView
    lateinit var textViewVersionName: TextView
    lateinit var textViewUserId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SmokSmogApplication[activity]
                .appComponent
                .inject(this)
    }

    override fun getLayoutId(): Int = R.layout.dialog_info_about

    override fun initView(view: View) {
        super.initView(view)

        with(view) {
            textView = findViewById(R.id.textView) as TextView
            textViewUserId = findViewById(R.id.textViewUserId) as TextView
            textViewVersionName = findViewById(R.id.textViewVersionName) as TextView
        }

        textView.compatFromHtml(R.string.about)
        textView.movementMethod = LinkMovementMethod.getInstance()

        try {
            val packageInfo = activity.packageManager.getPackageInfo(activity.packageName, 0)
            textViewVersionName.text = getString(R.string.version_name_and_code,
                    packageInfo.versionName,
                    packageInfo.versionCode)
        } catch (e: Exception) {
            logger.i(tag(), "Problem with obtaining version", e)
        }

        textViewUserId.text = user.identifier
    }
}
