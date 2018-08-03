package com.test.roompagingtest;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.roompagingtest.model.TestEntity;
import com.test.toompagedadaptertest.R;

public class MainAdapter extends PagedListAdapter<TestEntity, MainAdapter.TestEntityViewHolder> {

    public MainAdapter() {
        super(new DiffUtil.ItemCallback<TestEntity>() {
            @Override
            public boolean areItemsTheSame(TestEntity oldItem, TestEntity newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(TestEntity oldItem, TestEntity newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public MainAdapter.TestEntityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_test_entity, parent, false);
        return new TestEntityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.TestEntityViewHolder holder, int position) {
        holder.setTestEntity(getItem(position));
    }

    public static class TestEntityViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;

        private TestEntity mTestEntity;

        public TestEntityViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView;
        }

        public void setTestEntity(TestEntity testEntity) {
            mTestEntity = testEntity;

            if (mTestEntity == null) {
                mTitle.setText("");
            } else {
                mTitle.setText(Integer.toString(mTestEntity.getIndexInResponse()));
            }
        }

    }

}
