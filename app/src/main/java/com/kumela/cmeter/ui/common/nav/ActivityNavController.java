package com.kumela.cmeter.ui.common.nav;

import android.content.Context;
import android.content.Intent;

import java.util.Map;

/**
 * Created by Toko on 10,July,2020
 **/

public abstract class ActivityNavController {
    private Context mContext;

    protected void setContext(Context context) {
        this.mContext = context;
    }

    protected Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("setContext() must be called before calling getter method");
        }
        return mContext;
    }

    protected void startActivity(Intent intent) {
        if (mContext == null) {
            throw new RuntimeException("setContext() must be called before calling startActivity()");
        }
        mContext.startActivity(intent);
    }
}
