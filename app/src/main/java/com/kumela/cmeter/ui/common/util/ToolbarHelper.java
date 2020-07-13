package com.kumela.cmeter.ui.common.util;

import android.view.View;

/**
 * Created by Toko on 10,July,2020
 **/

public interface ToolbarHelper {
    void setTitle(String title);

    <T extends View> T findMenuView(int id);
}
