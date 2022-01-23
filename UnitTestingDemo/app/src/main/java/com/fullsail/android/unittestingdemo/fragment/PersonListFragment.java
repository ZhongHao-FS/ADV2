package com.fullsail.android.unittestingdemo.fragment;

import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.widget.ArrayAdapter;

import com.fullsail.android.unittestingdemo.data.Person;
import com.fullsail.android.unittestingdemo.util.StorageUtil;

import java.util.ArrayList;

public class PersonListFragment extends ListFragment {
    public static final String TAG = "PersonListFragment.TAG";

    public static PersonListFragment newInstance() {
        return new PersonListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Person> people = StorageUtil.loadPeople(getActivity());
        ArrayAdapter<Person> peopleAdapter = new ArrayAdapter<Person>(
                getActivity(), android.R.layout.simple_list_item_1, people);
        setListAdapter(peopleAdapter);
    }
}
