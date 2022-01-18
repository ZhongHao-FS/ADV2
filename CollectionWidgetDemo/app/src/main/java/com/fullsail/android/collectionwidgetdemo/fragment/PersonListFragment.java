package com.fullsail.android.collectionwidgetdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fullsail.android.collectionwidgetdemo.data.Person;
import com.fullsail.android.collectionwidgetdemo.data.PersonHelper;

import java.util.ArrayList;

public class PersonListFragment extends ListFragment {
    public static final String TAG = "PersonListFragment.TAG";

    public static PersonListFragment newInstance() {
        return new PersonListFragment();
    }

    public interface OnPersonClickedListener {
        void personClicked(Person p);
    }

    private OnPersonClickedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnPersonClickedListener) {
            mListener = (OnPersonClickedListener)context;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refresh();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Person person = (Person)l.getAdapter().getItem(position);
        if(mListener != null) {
            mListener.personClicked(person);
        }
    }

    public void refresh() {
        ArrayList<Person> people = PersonHelper.loadPeople(getActivity());
        ArrayAdapter<Person> peopleAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, people);
        setListAdapter(peopleAdapter);
    }
}
