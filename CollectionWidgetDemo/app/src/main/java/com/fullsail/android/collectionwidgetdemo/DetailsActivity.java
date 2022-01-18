package com.fullsail.android.collectionwidgetdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.android.collectionwidgetdemo.data.Person;
import com.fullsail.android.collectionwidgetdemo.data.PersonHelper;
import com.fullsail.android.collectionwidgetdemo.fragment.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "com.fullsail.android.EXTRA_PERSON";

    private Person mPerson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent starterIntent = getIntent();
        if(!starterIntent.hasExtra(EXTRA_PERSON)) {
            finish();
            return;
        }

        mPerson = (Person)starterIntent.getSerializableExtra(EXTRA_PERSON);

        if(savedInstanceState == null) {
            DetailsFragment fragment = DetailsFragment.newInstance(mPerson);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, DetailsFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_delete) {
            PersonHelper.deletePerson(this, mPerson);
            finish();
        }

        return true;
    }
}
