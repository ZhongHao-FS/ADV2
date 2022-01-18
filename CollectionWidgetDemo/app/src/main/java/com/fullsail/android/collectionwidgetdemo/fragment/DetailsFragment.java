package com.fullsail.android.collectionwidgetdemo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullsail.android.collectionwidgetdemo.R;
import com.fullsail.android.collectionwidgetdemo.data.Person;

public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";

    private static final String ARG_PERSON = "DetailsFragment.ARG_PERSON";

    public static DetailsFragment newInstance(Person _person) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON, _person);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if(args == null || !args.containsKey(ARG_PERSON)) {
            throw new IllegalArgumentException("Must pass a person into the details fragment.");
        }

        Person person = (Person)args.getSerializable(ARG_PERSON);

        View view = getView();
        if(view == null) {
            return;
        }
        TextView tv = (TextView)view.findViewById(R.id.text_first_name);
        tv.setText(person.getFirstName());

        tv = (TextView)view.findViewById(R.id.text_last_name);
        tv.setText(person.getLastName());

        tv = (TextView)view.findViewById(R.id.text_age);
        tv.setText("" + person.getAge());
    }
}
