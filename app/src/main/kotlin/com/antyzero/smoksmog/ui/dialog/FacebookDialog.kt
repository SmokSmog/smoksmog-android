package com.antyzero.smoksmog.ui.dialog

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView

import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent

import javax.inject.Inject

import butterknife.Bind
import butterknife.ButterKnife

/**
 * Simple info dialog for Facebook page
 */
class FacebookDialog : InfoDialog() {

    @Inject
    lateinit var answers: Answers

    @Bind(R.id.imageView)
    internal var imageView: ImageView? = null

    override fun getLayoutId(): Int {
        return R.layout.dialog_info_facebook
    }


    override fun updateBuilder(builder: AlertDialog.Builder): AlertDialog.Builder {
        builder.setPositiveButton("OK, pokaż") { dialog, which -> takeMeToFacebook() }.setNegativeButton("Nie, podziękuję") { dialog, which -> dismiss() }
        return builder
    }

    override fun initView(view: View) {
        super.initView(view)
        ButterKnife.bind(this, view)
        SmokSmogApplication[view.context].appComponent.inject(this)
        imageView!!.setOnClickListener { takeMeToFacebook() }
    }

    private fun takeMeToFacebook() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SmokSmog"))
        startActivity(browserIntent)
        dismiss()
        answers!!.logCustom(FacebookClickedEvent())
    }

    private class FacebookClickedEvent : CustomEvent(FacebookClickedEvent::class.java.simpleName)
}
