package com.antyzero.smoksmog.ui.dialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.antyzero.smoksmog.R;
import com.antyzero.smoksmog.SmokSmogApplication;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Simple info dialog for Facebook page
 */
public class FacebookDialog extends InfoDialog {

    @Inject
    Answers answers;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_info_facebook;
    }


    @Override
    protected AlertDialog.Builder updateBuilder(AlertDialog.Builder builder) {
        builder.setPositiveButton("OK, pokaż", (dialog, which) -> {
            takeMeToFacebook();
        }).setNegativeButton("Nie, podziękuję", (dialog, which) -> {
            dismiss();
        });
        return builder;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this, view);
        SmokSmogApplication.get(view.getContext()).getAppComponent().inject(this);
        imageView.setOnClickListener(v -> takeMeToFacebook());
    }

    private void takeMeToFacebook() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SmokSmog"));
        startActivity(browserIntent);
        dismiss();
        answers.logCustom(new FacebookClickedEvent());
    }

    private static class FacebookClickedEvent extends CustomEvent {

        public FacebookClickedEvent() {
            super(FacebookClickedEvent.class.getSimpleName());
        }
    }
}
