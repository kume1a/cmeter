package com.kumela.cmeter.ui.common.mvc.observanble;

import com.kumela.cmeter.ui.common.mvc.ViewMvc;

public interface BaseObservableViewMvc<ListenerType> extends ViewMvc {

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
