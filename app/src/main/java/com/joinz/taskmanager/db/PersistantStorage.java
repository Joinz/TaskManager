package com.joinz.taskmanager.db;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;

public class PersistantStorage {
    private static final String TAG = "PersistantStorage";
    private SharedPreferences sharedPreferences;

    public PersistantStorage() {
        sharedPreferences = App.getInstance().getSharedPreferences();
    }

    public Observable<Integer> getPropertyReactively(final String name) {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Log.d(TAG, "getPropertyReactively");
                return sharedPreferences.getInt(name, 0);
            }
        });
    }

    public Completable addPropertyReactively(final String name) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                int value = sharedPreferences.getInt(name, 0);
                sharedPreferences.edit().putInt(name, ++value).apply();
                Log.d(TAG, "addPropertyReactively");
            }
        });
    }
}

