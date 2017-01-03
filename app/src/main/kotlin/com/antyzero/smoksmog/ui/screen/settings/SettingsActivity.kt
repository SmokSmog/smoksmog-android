package com.antyzero.smoksmog.ui.screen.settings


import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setTitle(R.string.title_settings)
        }

        fragmentManager.beginTransaction()
                .replace(R.id.contentFragment, GeneralSettingsFragment.create())
                .commit()
    }

    companion object {

        /**
         * @param context for starting
         */
        fun start(context: Context) {
            context.startActivity(intent(context))
        }

        private fun intent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
}
