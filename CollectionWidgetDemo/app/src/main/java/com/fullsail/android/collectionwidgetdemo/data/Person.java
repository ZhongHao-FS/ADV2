package com.fullsail.android.collectionwidgetdemo.data;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {

    private String mFirstName;
    private String mLastName;
    private int mAge;

    public Person() {
        mFirstName = mLastName = "";
        mAge = -1;
    }

    public Person(String _first, String _last, int _age) {
        mFirstName = _first;
        mLastName = _last;
        mAge = _age;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public int getAge() {
        return mAge;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        }

        Person other = (Person) obj;
        return Objects.equals(mFirstName, other.mFirstName) &&
                Objects.equals(mLastName, other.mLastName) &&
                mAge == other.mAge;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " - " + mAge;
    }
}