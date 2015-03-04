package pl.malopolska.smoksmog.ui.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;

public class PollutionIndicatorView extends View {

    public static final float ANGLE_START = 120f;
    public static final float ANGLE_SWEEP = 300f;

    private Paint paintArcBackground;
    private Paint paintArcProgress;

    private RectF rectArc;

    private Path arcFull;
    private Path arcProgress;

    private float stroke;
    private float strokeHalf;

    private float value = 0f;

    public PollutionIndicatorView(Context context) {
        super(context);
        init(context);
    }

    public PollutionIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PollutionIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PollutionIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        stroke = 50;
        strokeHalf = stroke / 2;

        paintArcBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintArcBackground.setColor(Color.rgb(23, 24, 235));
        paintArcBackground.setStrokeWidth(stroke); // TODO dp
        paintArcBackground.setStyle(Paint.Style.STROKE);
        paintArcBackground.setStrokeCap(Paint.Cap.ROUND);
        paintArcBackground.setStrokeJoin(Paint.Join.ROUND);

        paintArcProgress = new Paint(paintArcBackground);
        paintArcProgress.setColor(Color.rgb(254, 23, 45));
    }

    public void setValue(float value){
        this.value = value;

        arcProgress = createArc(value);

        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {

        rectArc = new RectF(strokeHalf, strokeHalf, width - strokeHalf, height - strokeHalf);

        arcFull = createArc();

        arcProgress = createArc(value);

        super.onSizeChanged(width, height, oldw, oldh);
    }

    private Path createArc() {
        return createArc(1.0f);
    }

    private Path createArc(float percent) {

        float value = percent;

        if (percent != 1f) {
            value = new BigDecimal(percent).abs().remainder(BigDecimal.ONE).floatValue();
        }

        float sweep = Math.min(ANGLE_SWEEP * value, ANGLE_SWEEP);

        Path path = new Path();
        path.addArc(rectArc, ANGLE_START, sweep);

        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(arcFull, paintArcBackground);
        canvas.drawPath(arcProgress, paintArcProgress);
    }
}
