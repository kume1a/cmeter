package com.kumela.cmeter.ui.common.mvc.observanble;

import com.kumela.cmeter.ui.common.mvc.BaseViewMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableViewMvcImpl<ListenerType> extends BaseViewMvc
        implements BaseObservableViewMvc<ListenerType> {

    private Set<ListenerType> mListeners = new HashSet<>();

    @Override
    public final void registerListener(ListenerType listener) {
        mListeners.add(listener);
    }

    @Override
    public final void unregisterListener(ListenerType listener) {
        mListeners.remove(listener);
    }

    protected final Set<ListenerType> getListeners() {
        return Collections.unmodifiableSet(mListeners);
    }
}
