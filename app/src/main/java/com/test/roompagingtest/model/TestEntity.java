package com.test.roompagingtest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity
public class TestEntity {

    @PrimaryKey
    @NonNull
    private String mId;
    private int mIndexInResponse;

    public TestEntity(@NonNull String id, int indexInResponse) {
        mId = id;
        mIndexInResponse = indexInResponse;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public int getIndexInResponse() {
        return mIndexInResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return mIndexInResponse == that.mIndexInResponse &&
                Objects.equals(mId, that.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mIndexInResponse);
    }
}
