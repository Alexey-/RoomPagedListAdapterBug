package com.test.roompagingtest.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class Executors {

    private static class MainThreadExecutor implements Executor {

        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable action) {
            mHandler.post(action);
        }

    }

    private static MainThreadExecutor sMainThreadExecutor = new MainThreadExecutor();
    private static Executor sBackgroundExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();

    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    public static Executor getBackgroundExecutor() {
        return sBackgroundExecutor;
    }

}
