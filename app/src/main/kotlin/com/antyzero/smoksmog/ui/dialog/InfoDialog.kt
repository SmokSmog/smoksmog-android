package com.antyzero.smoksmog.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import com.antyzero.smoksmog.dsl.setNegativeButton
import com.antyzero.smoksmog.dsl.setPositiveButton
import com.antyzero.smoksmog.dsl.setToolbar

abstract class InfoDialog : DialogFragment() {

    lateinit protected var toolbar: Toolbar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).apply {
            toolbar = setToolbar(dataToolbarTitle())
            setView(dataContent())
            setPositiveButton(dataPositiveButton())
            setNegativeButton(dataNegativeButton())
        }.create()
    }

    abstract protected fun dataToolbarTitle(): String
    abstract protected fun dataPositiveButton(): Pair<String, DialogInterface.OnClickListener>
    abstract protected fun dataNegativeButton(): Pair<String, DialogInterface.OnClickListener>
    abstract protected fun dataContent(): View
}