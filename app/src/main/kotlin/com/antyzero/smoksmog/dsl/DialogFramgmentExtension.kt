package com.antyzero.smoksmog.dsl

import android.app.FragmentManager
import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import com.antyzero.smoksmog.R

fun AlertDialog.Builder.setToolbar(toolbarText: String): Toolbar {
    val toolbar = LayoutInflater.from(context).inflate(R.layout.dialog_toolbar, null) as Toolbar
    toolbar.setTitleTextColor(context.getCompatColor(smoksmog.R.color.text_light))
    toolbar.setBackgroundColor(context.getCompatColor(smoksmog.R.color.primaryDark))
    toolbar.title = toolbarText
    this.setCustomTitle(toolbar)
    return toolbar
}

fun AlertDialog.Builder.setPositiveButton(pair: Pair<String, DialogInterface.OnClickListener>): AlertDialog.Builder {
    setPositiveButton(pair.first, pair.second)
    return this
}

fun AlertDialog.Builder.setNegativeButton(pair: Pair<String, DialogInterface.OnClickListener>): AlertDialog.Builder {
    setNegativeButton(pair.first, pair.second)
    return this
}

fun Fragment.layoutInflater() = activity.layoutInflater

fun DialogFragment.show(appCompatActivity: AppCompatActivity, tag: String) = this.show(appCompatActivity.supportFragmentManager, tag)

fun android.app.DialogFragment.show(fragmentManager: FragmentManager) = this.show(fragmentManager, this.tag())