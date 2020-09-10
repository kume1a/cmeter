package com.kumela.cmeter.ui.common.mvc.observanble;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Toko on 03,August,2020
 **/

public abstract class ObservableViewModel<LISTENER> extends ViewModel {
    private final Object mListenerLock = new Object();
    private final Object mSnapshotLock = new Object();

    private final Set<LISTENER> mListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<>(1));

    private final Set<ListenerRegistration> mSnapshotListeners = Collections.newSetFromMap(
            new ConcurrentHashMap<>());


    public final void registerListener(LISTENER listener) {
        synchronized (mListenerLock) {
            mListeners.add(listener);
        }
    }

    public final void unregisterListener(LISTENER listener) {
        synchronized (mListenerLock) {
            mListeners.remove(listener);
        }
    }

    protected final Set<LISTENER> getListeners() {
        synchronized (mListenerLock) {
            return Collections.unmodifiableSet(new HashSet<>(mListeners));
        }
    }

    protected final void registerSnapshotListener(ListenerRegistration listenerRegistration) {
        mSnapshotListeners.add(listenerRegistration);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        for (ListenerRegistration listenerRegistration : mSnapshotListeners) {
            listenerRegistration.remove();
        }
    }
}
