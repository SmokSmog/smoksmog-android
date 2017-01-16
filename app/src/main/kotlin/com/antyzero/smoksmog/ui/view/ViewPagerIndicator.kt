package com.antyzero.smoksmog.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.dsl.getCompatColor
import java.util.*


/**

 */
class ViewPagerIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    private var stationIds: List<Long> = ArrayList()

    private var itemSpacing = 0
    private var itemSideSize = 0
    private var itemSideHalfSize = 0

    private var desiredWidth = 0
    private var desiredHeight = 0

    private var arrow: Path = Path()
    private var paintActiveIndicator: Paint = Paint()
    private var paintIndicator: Paint

    private var activePosition = 0

    init {

        itemSpacing = context.resources.getDimensionPixelSize(R.dimen.indicator_item_spacing)
        itemSideSize = context.resources.getDimensionPixelSize(R.dimen.indicator_item_size)
        itemSideHalfSize = itemSideSize / 2

        paintActiveIndicator.color = context.getCompatColor(R.color.iron)
        paintActiveIndicator.style = Paint.Style.FILL
        paintActiveIndicator.strokeWidth = 0f
        paintActiveIndicator.isAntiAlias = true

        paintIndicator = Paint(paintActiveIndicator)
        paintIndicator.alpha = 127

        arrow.reset()
        arrow.moveTo(itemSideHalfSize.toFloat(), itemSideHalfSize.toFloat())
        arrow.lineTo(itemSideHalfSize.toFloat(), itemSideSize.toFloat())
        arrow.lineTo(itemSideSize.toFloat(), 0f)
        arrow.lineTo(0f, itemSideHalfSize.toFloat())
        arrow.close()
    }

    fun setStationIds(stationIds: List<Long>) {
        this.stationIds = stationIds
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        desiredHeight = 0
        desiredWidth = 0

        if (!stationIds.isEmpty()) {
            desiredHeight = itemSideSize
            desiredWidth = itemSideSize
            for (i in 1..stationIds.size - 1) {
                desiredWidth += itemSideSize + itemSpacing
            }
        }

        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in stationIds.indices) {
            var paint: Paint = paintIndicator

            if (i == activePosition) {
                paint = paintActiveIndicator
            }

            if (stationIds[i] <= 0) {
                canvas.save()
                canvas.translate(((itemSideSize + itemSpacing) * i).toFloat(), 0f)
                canvas.drawPath(arrow!!, paint)
                canvas.restore()
            } else {
                val cx = itemSideHalfSize + (itemSideSize + itemSpacing) * i
                val cy = this.itemSideHalfSize
                canvas.drawCircle(cx.toFloat(), cy.toFloat(), this.itemSideHalfSize.toFloat(), paint)
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (position != activePosition) {
            activePosition = position
            postInvalidate()
            // TODO we may ask to redraw only specific areas ?
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
