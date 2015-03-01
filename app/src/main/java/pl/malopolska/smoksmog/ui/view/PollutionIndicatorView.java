package pl.malopolska.smoksmog.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class PollutionIndicatorView extends View {

    public PollutionIndicatorView(Context context) {
        super(context);
    }

    public PollutionIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PollutionIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PollutionIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
