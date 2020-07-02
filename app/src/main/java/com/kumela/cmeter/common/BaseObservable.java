package com.kumela.cmeter.common;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseObservable<LISTENER> {

    private final Set<LISTENER> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<>(1));


    public final void registerListener(LISTENER listener) {
        mListeners.add(listener);
    }

    public final void unregisterListener(LISTENER listener) {
        mListeners.remove(listener);
    }

    protected final Set<LISTENER> getListeners() {
        return Collections.unmodifiableSet(mListeners);
    }

}
