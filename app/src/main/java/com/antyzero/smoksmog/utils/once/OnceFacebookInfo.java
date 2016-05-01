package com.antyzero.smoksmog.utils.once;

import com.antyzero.smoksmog.eventbus.RxBus;
import com.antyzero.smoksmog.ui.dialog.FacebookDialog;
import com.antyzero.smoksmog.ui.dialog.InfoDialog;

public class OnceFacebookInfo {

    private static final String TAG = OnceFacebookInfo.class.getSimpleName();
    private final RxBus rxBus;

    public OnceFacebookInfo(RxBus rxBus) {
        this.rxBus = rxBus;
    }

    public void doIt() {
        rxBus.send(new InfoDialog.Event<>(FacebookDialog.class));
    }
}
