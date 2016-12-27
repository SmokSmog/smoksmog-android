package com.antyzero.smoksmog.ui.screen.start

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.eventbus.RxBus
import com.antyzero.smoksmog.firebase.FirebaseEvents
import com.antyzero.smoksmog.permission.PermissionHelper
import com.antyzero.smoksmog.settings.SettingsHelper
import com.antyzero.smoksmog.ui.BaseDragonActivity
import com.antyzero.smoksmog.ui.dialog.AboutDialog
import com.antyzero.smoksmog.ui.dialog.InfoDialog
import com.antyzero.smoksmog.ui.screen.ActivityModule
import com.antyzero.smoksmog.ui.screen.order.OrderActivity
import com.antyzero.smoksmog.ui.screen.settings.SettingsActivity
import com.antyzero.smoksmog.ui.screen.start.model.StationIdList
import com.antyzero.smoksmog.ui.typeface.TypefaceProvider
import com.trello.rxlifecycle.ActivityEvent
import kotlinx.android.synthetic.main.activity_start.*
import pl.malopolska.smoksmog.SmokSmog
import rx.android.schedulers.AndroidSchedulers
import smoksmog.logger.Logger
import javax.inject.Inject

/**
 * Base activity for future
 */
class StartActivity : BaseDragonActivity(), ViewPager.OnPageChangeListener {

    @Inject lateinit var smokSmog: SmokSmog
    @Inject lateinit var logger: Logger
    @Inject lateinit var errorReporter: ErrorReporter
    @Inject lateinit var settingsHelper: SettingsHelper
    @Inject lateinit var rxBus: RxBus
    @Inject lateinit var typefaceProvider: TypefaceProvider
    @Inject lateinit var firebaseEvents: FirebaseEvents

    private lateinit var pageSave: PageSave
    private lateinit var stationIds: List<Long>
    private lateinit var stationSlideAdapter: StationSlideAdapter

    private var lastPageSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageSave = PageSave(this)

        SmokSmogApplication[this].appComponent
                .plus(ActivityModule(this)).let {
            it.inject(this@StartActivity)
            val permissionHelper = PermissionHelper(this@StartActivity)
            stationIds = StationIdList(SettingsHelper(this@StartActivity, permissionHelper), permissionHelper)
        }

        setContentView(R.layout.activity_start)
        setSupportActionBar(toolbar)

        buttonAddStation.setOnClickListener { OrderActivity.start(this, true) }

        stationSlideAdapter = StationSlideAdapter(fragmentManager, stationIds)

        viewPager.adapter = stationSlideAdapter
        viewPager.offscreenPageLimit = PAGE_LIMIT
        viewPager.addOnPageChangeListener(this)
        viewPager.addOnPageChangeListener(viewPagerIndicator)
        viewPager.currentItem = pageSave.restorePage()

        viewPagerIndicator!!.setStationIds(stationIds)

        correctTitleMargin()

        // Listen for info dialog calls
        rxBus.toObserverable()
                .compose(bindUntilEvent<Any>(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ event ->
                    if (event is InfoDialog.Event<*>) {
                        InfoDialog.show(fragmentManager, event)
                    }
                },
                        { throwable -> logger.w("RxEventBus", "Unable to process event", throwable) })

        // Listen for title updates
        rxBus.toObserverable()
                .compose(bindUntilEvent<Any>(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ event ->
                    if (event is TitleUpdateEvent) {
                        updateTitleWithStation()
                    }
                },
                        { throwable -> logger.w("RxEventBus", "Unable to process event", throwable) })

        if (savedInstanceState != null) {
            lastPageSelected = savedInstanceState.getInt(KEY_LAST_PAGE, 0)
            viewPager.setCurrentItem(lastPageSelected, true)
        }
    }

    override fun onResume() {
        super.onResume()
        viewPagerIndicator.setStationIds(stationIds)
        stationSlideAdapter.notifyDataSetChanged()
        updateTitleWithStation()

        if (stationIds.isEmpty()) {
            visibleNoStations()
        } else {
            visibleStations()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    /**
     * Because it's not aligned with main layout margin
     */
    private fun correctTitleMargin() {
        toolbar!!.setContentInsetsAbsolute(16, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> SettingsActivity.start(this)
            R.id.action_order -> OrderActivity.start(this)
            R.id.action_facebook -> goToFacebookSite()
            R.id.action_beta -> goToBetaLogin()
            R.id.action_about -> rxBus.send(InfoDialog.Event(AboutDialog::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToBetaLogin() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/apps/testing/pl.malopolska.smoksmog")))
    }

    private fun goToFacebookSite() {
        val facebookUrl = "https://fb.com/SmokSmog"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        try {
            if (packageManager.getPackageInfo("com.facebook.katana", 0) != null) {
                intent.data = Uri.parse("fb://page/714218922054053")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // do nothing
        }

        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_LAST_PAGE, lastPageSelected)
        super.onSaveInstanceState(outState)
    }

    override fun onPageScrollStateChanged(state: Int) {
        // do nothing
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // do nothing
    }

    override fun onPageSelected(position: Int) {
        updateTitleWithStation(position)
        lastPageSelected = position
        pageSave.savePage(position)
        firebaseEvents.logStationCardInView(stationSlideAdapter.getItem(position).stationId)
    }

    private fun updateTitleWithStation() {
        if (stationSlideAdapter.count > 0) {
            updateTitleWithStation(viewPager.currentItem)
        }
    }

    fun updateTitleWithStation(position: Int) {
        stationSlideAdapter.getFragmentReference(position)?.get()?.let {
            val stationFragment = it
            it.title.let {
                toolbar.title = it
                toolbar.subtitle = stationFragment.subtitle
                changeSubtitleTypeface()
            }
        }
    }

    private fun visibleStations() {
        viewSwitcher!!.displayedChild = 0
    }

    private fun visibleNoStations() {
        if (supportActionBar != null) {
            supportActionBar!!.setTitle(R.string.app_name)
            supportActionBar!!.subtitle = null
        }
        viewSwitcher!!.displayedChild = 1
    }

    /**
     * This is messy, Calligraphy should handle this but for some reason it's the only TextView
     * not updated with default font.
     *
     *
     * TODO fix with Calligraphy in future
     */
    private fun changeSubtitleTypeface() {
        (1..toolbar.childCount - 1)
                .map { toolbar!!.getChildAt(it) }
                .filterIsInstance<TextView>()
                .forEach { it.typeface = typefaceProvider.default }
    }

    /**
     * Ask to check title and update is possible
     */
    class TitleUpdateEvent

    companion object {

        private val KEY_LAST_PAGE = "lastSelectedPagePosition"
        private val PAGE_LIMIT = 5
    }
}
