package com.antyzero.smoksmog.ui.dialog;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.antyzero.smoksmog.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Simple info dialog for Facebook page
 */
public class FacebookDialog extends InfoDialog {

    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_info_facebook;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        imageView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SmokSmog"));
            startActivity(browserIntent);
        });
    }
}
