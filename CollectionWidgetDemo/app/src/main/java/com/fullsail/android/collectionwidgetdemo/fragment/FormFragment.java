package com.fullsail.android.collectionwidgetdemo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fullsail.android.collectionwidgetdemo.R;
import com.fullsail.android.collectionwidgetdemo.data.Person;

public class FormFragment extends Fragment {
    public static final String TAG = "FormFragment.TAG";

    public static FormFragment newInstance() {
        return new FormFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    public Person getPerson() {
        View view = getView();
        if (view == null) {
            return null;
        }

        EditText firstNameEdit = (EditText) view.findViewById(R.id.edit_first_name);
        EditText lastNameEdit = (EditText) view.findViewById(R.id.edit_last_name);
        EditText ageEdit = (EditText) view.findViewById(R.id.edit_age);

        String firstName = firstNameEdit.getText().toString().trim();
        String lastName = lastNameEdit.getText().toString().trim();
        String ageString = ageEdit.getText().toString().trim();
        int age = -1;

        try {
            age = Integer.parseInt(ageString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (firstName.length() == 0 || lastName.length() == 0 || age == -1) {
            return null;
        }

        return new Person(firstName, lastName, age);
    }
}
