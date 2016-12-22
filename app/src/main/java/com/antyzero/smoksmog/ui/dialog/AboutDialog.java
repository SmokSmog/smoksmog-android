package com.antyzero.smoksmog.ui.dialog;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.antyzero.smoksmog.user.User;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import smoksmog.logger.Logger;

public class AboutDialog extends InfoDialog {

    private static final String TAG = AboutDialog.class.getSimpleName();

    @Inject
    Logger logger;
    @Inject
    User user;

    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.textViewVersionName)
    TextView textViewVersionName;
    @Bind(R.id.textViewUserId)
    TextView textViewUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmokSmogApplication.Companion.get(getActivity())
                .getAppComponent()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_info_about;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this, view);

        textView.setText(Html.fromHtml(getString(R.string.about)));
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            textViewVersionName.setText(getString(R.string.version_name_and_code,
                    packageInfo.versionName,
                    packageInfo.versionCode));
        } catch (Exception e) {
            logger.i(TAG, "Problem with obtaining version", e);
        }

        textViewUserId.setText(user.getIdentifier());
    }


}
