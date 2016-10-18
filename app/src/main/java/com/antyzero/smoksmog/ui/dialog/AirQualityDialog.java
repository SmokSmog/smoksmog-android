package com.antyzero.smoksmog.ui.dialog;


import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.antyzero.smoksmog.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AirQualityDialog extends InfoDialog {

    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_info_air_quality;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this, view);
        textView.setText(Html.fromHtml(getString(R.string.desc_air_quality)));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
