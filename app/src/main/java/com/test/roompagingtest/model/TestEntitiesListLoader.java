package com.test.roompagingtest.model;

import com.test.roompagingtest.utils.Executors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestEntitiesListLoader {

    public interface OnLoadingCompleteListener {
        void onLoadingComplete();
    }

    private MainDatabase mMainDatabase;
    private OnLoadingCompleteListener mLoadingCompleteListener;

    public TestEntitiesListLoader(MainDatabase mainDatabase, OnLoadingCompleteListener loadingCompleteListener) {
        mMainDatabase = mainDatabase;
        mLoadingCompleteListener = loadingCompleteListener;
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        Executors.getBackgroundExecutor().execute(() -> {
            // emulate network delay
            sleep(300);

            List<TestEntity> entities = new ArrayList<>();
            for (int i = 0; i < 20; ++i) {
                entities.add(new TestEntity(UUID.randomUUID().toString(), i));
            }

            mMainDatabase.runInTransaction(() -> {
                mMainDatabase.getTestEntitiesDao().deleteTestEntities();
                mMainDatabase.getTestEntitiesDao().saveTestEntities(entities);
            });

            // https://issuetracker.google.com/issues/112158846
            sleep(100);

            mLoadingCompleteListener.onLoadingComplete();
        });
    }

    public void loadMore() {
        Executors.getBackgroundExecutor().execute(() -> {
            // emulate network delay
            sleep(300);

            int curentMaxIndex = mMainDatabase.getTestEntitiesDao().getMaxLoadedIndex();
            List<TestEntity> entities = new ArrayList<>();
            for (int i = 0; i < 20; ++i) {
                entities.add(new TestEntity(UUID.randomUUID().toString(), i + curentMaxIndex + 1));
            }

            mMainDatabase.runInTransaction(() -> {
                mMainDatabase.getTestEntitiesDao().saveTestEntities(entities);
            });

            // https://issuetracker.google.com/issues/112158846
            sleep(100);

            mLoadingCompleteListener.onLoadingComplete();
        });
    }

}
