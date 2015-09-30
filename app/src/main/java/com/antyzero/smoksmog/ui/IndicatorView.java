package com.antyzero.smoksmog.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class IndicatorView extends View {

    private static final float GAP_ANGLE_DEFAULT = 90;
    private static final int STROKE_WIDTH_DEFAULT = 10;

    private float gapAngle = GAP_ANGLE_DEFAULT;

    private final Rect textBounds = new Rect();

    private Paint paintArcBackground;
    private Paint paintArcForeground;
    private Paint paintText;

    private float startAngle;
    private float sweepAngle;
    private float strokeWidth;
    private float textSize;
    private float canvasHorizontalMiddle;
    private float canvasVerticalMiddle;

    private int currentWidth;
    private int currentHeight;

    private RectF arcRect;

    public IndicatorView( Context context ) {
        super( context );
        init( context );
    }

    public IndicatorView( Context context, AttributeSet attrs ) {
        super( context, attrs );
        init( context );
    }

    public IndicatorView( Context context, AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        init( context );
    }

    @SuppressWarnings( "unused" ) @TargetApi(21)
    public IndicatorView( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes ) {
        super( context, attrs, defStyleAttr, defStyleRes );
        init( context );
    }

    private void init( Context context ) {

        strokeWidth = dipToPixels( context, STROKE_WIDTH_DEFAULT );

        // Init paints

        paintArcBackground = new Paint();
        paintArcBackground.setAntiAlias( true );
        paintArcBackground.setStrokeCap( Paint.Cap.ROUND );
        paintArcBackground.setStrokeWidth( strokeWidth - 2 );
        paintArcBackground.setStyle( Paint.Style.STROKE );

        paintArcForeground = new Paint( paintArcBackground );
        paintArcForeground.setAntiAlias( true );
        paintArcForeground.setStrokeCap( Paint.Cap.ROUND );
        paintArcForeground.setStyle( Paint.Style.STROKE );
        paintArcForeground.setColor( Color.CYAN );
        paintArcForeground.setStrokeWidth( strokeWidth );

        paintText = new Paint();
        paintText.setAntiAlias( true );
        paintText.setColor( Color.RED );
        paintText.setStyle( Paint.Style.FILL );
        paintText.setTypeface( Typeface.defaultFromStyle( Typeface.BOLD ) );
    }

    @Override
    protected void onSizeChanged( int w, int h, int oldw, int oldh ) {
        super.onSizeChanged( w, h, oldw, oldh );
        currentWidth = w;
        currentHeight = h;
        calculateDrawingVariables();
    }

    private static float dipToPixels( Context context, float dipValue ) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics );
    }

    /**
     * Recalculate some variables
     */
    private void calculateDrawingVariables() {

        startAngle = 90f + gapAngle / 2;
        sweepAngle = 360 - gapAngle;

        final float halfStrokeWidth = strokeWidth / 2;

        //noinspection SuspiciousNameCombination
        arcRect = new RectF(halfStrokeWidth,halfStrokeWidth,
                currentWidth - halfStrokeWidth,currentWidth - halfStrokeWidth);

        textSize = currentHeight / 2;

        canvasHorizontalMiddle = currentWidth / 2;
        canvasVerticalMiddle = currentWidth / 2;

        paintText.setTextSize( textSize );

        postInvalidateOnAnimation();
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        super.onDraw( canvas );

        canvas.drawArc( arcRect, startAngle, sweepAngle, false, paintArcBackground );
        canvas.drawArc( arcRect, startAngle, 87, false, paintArcForeground );

        drawTextCentred( canvas, paintText, "123", canvasHorizontalMiddle, canvasVerticalMiddle );
    }

    private void drawTextCentred( Canvas canvas, Paint paint, String text, float cx, float cy ) {
        paint.getTextBounds( text, 0, text.length(), textBounds );
        canvas.drawText( text, cx - textBounds.exactCenterX(), cy - textBounds.exactCenterY(), paint );
    }
}
