package com.fullsail.android.collectionwidgetdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.android.collectionwidgetdemo.data.Person;
import com.fullsail.android.collectionwidgetdemo.data.PersonHelper;
import com.fullsail.android.collectionwidgetdemo.fragment.FormFragment;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            FormFragment fragment = FormFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, FormFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_save) {
            FormFragment fragment = (FormFragment)getSupportFragmentManager()
                    .findFragmentByTag(FormFragment.TAG);

            if(fragment != null) {
                Person person = fragment.getPerson();
                if(person != null) {
                    PersonHelper.savePerson(this, person);
                    finish();
                }
            }
        }

        return true;
    }
}