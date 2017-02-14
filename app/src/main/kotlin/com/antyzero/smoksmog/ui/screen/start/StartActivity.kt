package com.antyzero.smoksmog.ui.screen.start

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmog
import com.antyzero.smoksmog.SmokSmogApplication
import com.antyzero.smoksmog.dsl.observable
import com.antyzero.smoksmog.dsl.tag
import com.antyzero.smoksmog.dsl.toast
import com.antyzero.smoksmog.error.ErrorReporter
import com.antyzero.smoksmog.eventbus.RxBus
import com.antyzero.smoksmog.firebase.FirebaseEvents
import com.antyzero.smoksmog.ui.BaseDragonActivity
import com.antyzero.smoksmog.ui.dialog.AboutDialog
import com.antyzero.smoksmog.ui.dialog.InfoDialog
import com.antyzero.smoksmog.ui.screen.ActivityModule
import com.antyzero.smoksmog.ui.screen.order.OrderActivity
import com.antyzero.smoksmog.ui.screen.settings.SettingsActivity
import com.antyzero.smoksmog.ui.typeface.TypefaceProvider
import com.trello.rxlifecycle.android.ActivityEvent
import kotlinx.android.synthetic.main.activity_start.*
import pl.malopolska.smoksmog.Api
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.cast
import rx.schedulers.Schedulers
import smoksmog.logger.Logger
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


/**
 * Base activity for future
 */
class StartActivity : BaseDragonActivity(), ViewPager.OnPageChangeListener {

    @Inject lateinit var api: Api
    @Inject lateinit var logger: Logger
    @Inject lateinit var errorReporter: ErrorReporter
    @Inject lateinit var rxBus: RxBus
    @Inject lateinit var typefaceProvider: TypefaceProvider
    @Inject lateinit var firebaseEvents: FirebaseEvents
    @Inject lateinit var smokSmog: SmokSmog

    private lateinit var pageSave: PageSave
    private lateinit var stationSlideAdapter: StationSlideAdapter

    private var lastPageSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageSave = PageSave(this)

        SmokSmogApplication[this].appComponent
                .plus(ActivityModule(this))
                .inject(this)

        setContentView(R.layout.activity_start)
        setSupportActionBar(toolbar)

        buttonAddStation.setOnClickListener { OrderActivity.start(this, true) }

        stationSlideAdapter = StationSlideAdapter(fragmentManager, smokSmog.storage.fetchAll())

        viewPager.adapter = stationSlideAdapter
        viewPager.offscreenPageLimit = PAGE_LIMIT
        viewPager.addOnPageChangeListener(this)
        viewPager.addOnPageChangeListener(viewPagerIndicator)
        viewPager.currentItem = pageSave.restorePage()

        viewPagerIndicator.setStationIds(smokSmog.storage.fetchAll())

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
        viewPagerIndicator.setStationIds(smokSmog.storage.fetchAll())
        stationSlideAdapter.notifyDataSetChanged()
        updateTitleWithStation()

        if (smokSmog.storage.fetchAll().isEmpty()) {
            visibleNoStations()
        } else {
            visibleStations()
        }
    }

    /**
     * Because it's not aligned with main layout margin
     */
    private fun correctTitleMargin() {
        toolbar.setContentInsetsAbsolute(16, 0)
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
            R.id.action_share -> shareScreen()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun shareScreen() {

        window.decorView.findViewById(android.R.id.content).observable()
                .map { getScreenShot(it) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .map { store(it) }
                .filter { it != null }
                .cast<File>()
                .map { shareImage(it) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    toast(if (it) {
                        "Shared success"
                    } else {
                        "Shared failed"
                    })
                }, {
                    toast("Fatal issue " + it.toString())
                    logger.e(tag(), "Fatal error", it)
                })
    }

    private fun getScreenShot(view: View): Bitmap {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun store(bm: Bitmap, fileName: String = File.createTempFile(System.currentTimeMillis().toString(), ".png").name): File? {
        val dirPath = externalCacheDir.absolutePath + "/Screenshots"
        val dir = File(dirPath).apply {
            if (!exists()) {
                mkdirs()
            }
        }
        val file = File(dir, fileName).apply {
            createNewFile()
        }
        val fOut = FileOutputStream(file)
        bm.compress(Bitmap.CompressFormat.PNG, 85, fOut)
        fOut.flush()
        fOut.close()
        return file
    }

    private fun shareImage(file: File): Boolean {
        val uri = Uri.fromFile(file)
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/png"

        System.out.println(">>> $file")
        System.out.println(">>> ${file.length()}")

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Text")
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"))
        } catch (e: ActivityNotFoundException) {
            return false
        }

        return true
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

        try {
            startActivity(intent)
        } catch (e: Exception) {
            logger.w(tag(), "Going to Facebook page went wrong ($intent)", e)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://fb.com/SmokSmog")))
        }

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
        viewSwitcher.displayedChild = 0
    }

    private fun visibleNoStations() {
        if (supportActionBar != null) {
            supportActionBar!!.setTitle(R.string.app_name)
            supportActionBar!!.subtitle = null
        }
        viewSwitcher.displayedChild = 1
    }

    /**
     * This is messy, Calligraphy should handle this but for some reason it's the only TextView
     * not updated with default font.
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
