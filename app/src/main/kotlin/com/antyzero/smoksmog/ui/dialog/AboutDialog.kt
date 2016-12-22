package com.antyzero.smoksmog.ui.dialog

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.user.User
import smoksmog.logger.Logger
import javax.inject.Inject

class AboutDialog : InfoDialog() {


    @Inject
    lateinit var logger: Logger
    @Inject
    lateinit var user: User

    @Bind(R.id.textView)
    internal var textView: TextView? = null
    @Bind(R.id.textViewVersionName)
    internal var textViewVersionName: TextView? = null
    @Bind(R.id.textViewUserId)
    internal var textViewUserId: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SmokSmogApplication[activity]
                .appComponent
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_info_about
    }

    override fun initView(view: View) {
        super.initView(view)
        ButterKnife.bind(this, view)

        textView!!.text = Html.fromHtml(getString(R.string.about))
        textView!!.movementMethod = LinkMovementMethod.getInstance()

        try {
            val packageInfo = activity.packageManager.getPackageInfo(activity.packageName, 0)
            textViewVersionName!!.text = getString(R.string.version_name_and_code,
                    packageInfo.versionName,
                    packageInfo.versionCode)
        } catch (e: Exception) {
            logger!!.i(TAG, "Problem with obtaining version", e)
        }

        textViewUserId!!.text = user!!.identifier
    }

    companion object {

        private val TAG = AboutDialog::class.java.simpleName
    }


}
