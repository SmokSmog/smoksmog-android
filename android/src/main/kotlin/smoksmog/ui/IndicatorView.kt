package smoksmog.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

import smoksmog.R

@Suppress("DEPRECATION")
class IndicatorView : View {

    private lateinit var paintValue: Paint
    private lateinit var paintArcBackground: Paint
    private lateinit var paintArcForeground: Paint

    private val valueAnimator = ValueAnimator.ofFloat(0f)
    private val gapAngle = GAP_ANGLE_DEFAULT
    private var paintNameShort: Paint? = null
    private var paintMaxValue: Paint? = null

    private var startAngle: Float = 0.toFloat()
    private var sweepAngle: Float = 0.toFloat()
    private var strokeWidth: Float = 0.toFloat()
    private var canvasHorizontalMiddle: Float = 0.toFloat()
    private var canvasVerticalMiddle: Float = 0.toFloat()

    private var currentWidth: Int = 0
    private var currentHeight: Int = 0

    private var value = 0f
    private var arcValue = 0f
    private var textNameSize = 10f

    private var arcRect = RectF()

    private var overLap = 0

    private var valueText = ""
    private var colorGood: Int = 0
    private var colorBad: Int = 0

    /**
     * Setup variables
     */
    init {
        valueAnimator.duration = 1000L
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {

        strokeWidth = dipToPixels(context, STROKE_WIDTH_DEFAULT.toFloat())
        textNameSize = dipToPixels(context, 32f)

        colorBad = context.resources.getColor(R.color.indicator_bad)
        colorGood = context.resources.getColor(R.color.indicator_good)

        // Init paints

        paintArcBackground = Paint()
        paintArcBackground.isAntiAlias = true
        paintArcBackground.strokeCap = Paint.Cap.ROUND
        paintArcBackground.strokeWidth = strokeWidth - 2
        paintArcBackground.style = Paint.Style.STROKE
        paintArcBackground.color = resources.getColor(R.color.iron)
        paintArcBackground.alpha = 104

        paintArcForeground = Paint(paintArcBackground)
        paintArcForeground.isAntiAlias = true
        paintArcForeground.strokeCap = Paint.Cap.ROUND
        paintArcForeground.style = Paint.Style.STROKE
        paintArcForeground.color = Color.parseColor("#5be6dc")
        paintArcForeground.strokeWidth = strokeWidth

        paintValue = Paint()
        paintValue.isAntiAlias = true
        paintValue.color = context.resources.getColor(R.color.iron)
        paintValue.style = Paint.Style.FILL
        paintValue.typeface = Typeface.createFromAsset(context.assets, "fonts/Lato-Light.ttf")

        paintMaxValue = Paint()
        paintMaxValue!!.isAntiAlias = true
        paintMaxValue!!.style = Paint.Style.FILL
        paintMaxValue!!.typeface = Typeface.defaultFromStyle(Typeface.BOLD)

        paintNameShort = Paint()
        paintNameShort!!.isAntiAlias = true
        paintNameShort!!.style = Paint.Style.FILL
        paintNameShort!!.textSize = textNameSize

        calculateMinorDrawingVariables()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val size: Int
        if (widthMode == View.MeasureSpec.EXACTLY && widthSize > 0) {
            size = widthSize
        } else if (heightMode == View.MeasureSpec.EXACTLY && heightSize > 0) {
            size = heightSize
        } else {
            size = if (widthSize < heightSize) widthSize else heightSize
        }

        val widthNewSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY)
        val heightNewSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthNewSpec, heightNewSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        currentWidth = w
        currentHeight = h
        calculateDrawingVariables()
    }

    /**
     * As 0% -> 100% -> more

     * @param newValue
     */
    fun setValue(newValue: Float) {

        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }

        valueAnimator.setFloatValues(this.value, newValue)
        valueAnimator.addUpdateListener { animation ->
            this@IndicatorView.value = animation.animatedValue as Float
            recalculateDuringAnimation()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Do nothing
            }

            override fun onAnimationEnd(animation: Animator) {
                // Do nothing
            }

            override fun onAnimationCancel(animation: Animator) {
                if (animation is ValueAnimator) {
                    this@IndicatorView.value = animation.animatedValue as Float
                }
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Do nothing
            }
        })
        valueAnimator.start()
    }

    /**
     * This should be called when animating values
     */
    private fun recalculateDuringAnimation() {

        this.arcValue = value * sweepAngle
        this.valueText = String.format("%.0f%%", value * 100)
        this.overLap = Math.floor(value.toDouble()).toInt()

        paintArcForeground.color = if (arcValue <= 360f)
            colorGood
        else
            Color.rgb(242, 162, 60)

        postInvalidateOnAnimation()
    }

    /**
     * Non ui dependant variables
     */
    private fun calculateMinorDrawingVariables() {
        startAngle = 270f + gapAngle / 2
        sweepAngle = 360 - gapAngle
    }

    /**
     * Recalculate some variables, important when view changes its size
     */
    private fun calculateDrawingVariables() {
        calculateMinorDrawingVariables()

        val halfStrokeWidth = strokeWidth / 2


        arcRect = RectF(
                halfStrokeWidth,
                halfStrokeWidth,
                currentWidth - halfStrokeWidth,
                currentWidth - halfStrokeWidth)

        canvasHorizontalMiddle = (currentWidth / 2).toFloat()
        canvasVerticalMiddle = (currentWidth / 2).toFloat()

        paintValue.textSize = (arcRect.bottom - arcRect.top) / 4f

        postInvalidateOnAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(arcRect, startAngle, sweepAngle, false, paintArcBackground)

        // TODO if overlap is > 0 draw pre background ?

        canvas.drawArc(arcRect, startAngle, arcValue, false, paintArcForeground)
        drawTextCentred(canvas, paintValue, valueText, arcRect)
    }

    private fun drawTextCentred(canvas: Canvas, paint: Paint, text: String, bounds: RectF) {
        drawTextCentred(canvas, paint, text, bounds.centerX(), bounds.centerY(), Rect())
    }

    private fun drawTextCentred(canvas: Canvas, paint: Paint, text: String, cx: Float, cy: Float, textBounds: Rect = Rect()) {
        paint.getTextBounds(text, 0, text.length, textBounds)
        canvas.drawText(text, cx - textBounds.exactCenterX(), cy - textBounds.exactCenterY(), paint)
    }

    companion object {

        private val GAP_ANGLE_DEFAULT = 0f
        private val STROKE_WIDTH_DEFAULT = 3

        private fun dipToPixels(context: Context, dipValue: Float): Float {
            val metrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
        }
    }
}
