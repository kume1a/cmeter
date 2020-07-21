package com.kumela.cmeter.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseObservable<LISTENER> {

    private final Object mLock = new Object();

    private final Set<LISTENER> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<>(1));


    public final void registerListener(LISTENER listener) {
        synchronized (mLock) {
            mListeners.add(listener);
        }
    }

    public final void unregisterListener(LISTENER listener) {
        synchronized (mLock) {
            mListeners.remove(listener);
        }
    }

    protected final Set<LISTENER> getListeners() {
        synchronized (mLock) {
            return Collections.unmodifiableSet(new HashSet<>(mListeners));
        }
    }

}
