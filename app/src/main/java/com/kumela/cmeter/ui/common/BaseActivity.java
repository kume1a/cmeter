package com.kumela.cmeter.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import com.kumela.cmeter.common.App;
import com.kumela.cmeter.common.di.ControllerCompositionRoot;

/**
 * Created by Toko on 22,June,2020
 **/

public class BaseActivity extends AppCompatActivity {
    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((App) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mControllerCompositionRoot;
    }

}
