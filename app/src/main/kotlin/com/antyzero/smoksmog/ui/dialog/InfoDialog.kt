package com.antyzero.smoksmog.ui.dialog


import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.view.View

/**
 * For info dialog
 */
abstract class InfoDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(getLayoutId(), null, false)
        initView(view)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
        return updateBuilder(builder).create()
    }

    /**
     * Override to change builder settings

     * @param builder
     * *
     * @return
     */
    protected open fun updateBuilder(builder: AlertDialog.Builder): AlertDialog.Builder {
        return builder
    }

    protected open fun initView(view: View) {
        // override if needed
    }

    protected abstract fun getLayoutId(): Int

    class Event<T : InfoDialog>(internal val dialogFragment: Class<T>)

    companion object {

        private val TAG = InfoDialog::class.java.simpleName

        fun show(fragmentManager: FragmentManager, event: Event<*>) {
            val infoDialog: InfoDialog

            try {
                infoDialog = event.dialogFragment.newInstance() as InfoDialog
            } catch (e: Exception) {
                throw IllegalStateException(
                        "Problem with creating fragment dialog " + event.dialogFragment.simpleName, e)
            }

            infoDialog.show(fragmentManager, TAG)
        }
    }
}
