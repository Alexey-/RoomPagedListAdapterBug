package com.test.roompagingtest;

import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.roompagingtest.model.MainDatabase;
import com.test.roompagingtest.model.TestEntitiesListLoader;
import com.test.roompagingtest.model.TestEntity;
import com.test.roompagingtest.model.TestEntityDao;
import com.test.roompagingtest.utils.EndlessScrollListener;
import com.test.roompagingtest.utils.Executors;
import com.test.toompagedadaptertest.R;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    private MainDatabase mMainDatabase;
    private TestEntityDao mTestEntityDao;
    private TestEntitiesListLoader mListLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);

        mMainDatabase = MainDatabase.getDatabase(this);
        mTestEntityDao = mMainDatabase.getTestEntitiesDao();
        mListLoader = new TestEntitiesListLoader(mMainDatabase, () -> {
            mSwipeRefreshLayout.setRefreshing(false);
            loadItemsIntoAdapter();
        });

        mAdapter = new MainAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mListLoader.update();
        });

        mRecyclerView.addOnScrollListener(new EndlessScrollListener(mRecyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mListLoader.loadMore();
            }
        });

        mListLoader.update();
    }

    private void loadItemsIntoAdapter() {
        runOnUiThread(() -> {
            PagedList<TestEntity> pagedList = new PagedList.Builder<>(mTestEntityDao.getTestEntities().create(), 20)
                    .setFetchExecutor(Executors.getBackgroundExecutor())
                    .setNotifyExecutor(Executors.getMainThreadExecutor())
                    .build();
            mAdapter.submitList(pagedList);
        });
    }

}
