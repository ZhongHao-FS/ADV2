package com.fullsail.android.collectionwidgetdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fullsail.android.collectionwidgetdemo.data.Person;
import com.fullsail.android.collectionwidgetdemo.fragment.PersonListFragment;

public class MainActivity extends AppCompatActivity
        implements PersonListFragment.OnPersonClickedListener {

    private static final int REQUEST_DETAILS = 0x01001;
    private static final int REQUEST_FORM = 0x01002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            PersonListFragment fragment = PersonListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, PersonListFragment.TAG)
                    .commit();
        } else {
            PersonListFragment fragment = (PersonListFragment)getSupportFragmentManager()
                    .findFragmentByTag(PersonListFragment.TAG);
            if(fragment != null) {
                fragment.refresh();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PersonListFragment fragment = (PersonListFragment)getSupportFragmentManager()
                .findFragmentByTag(PersonListFragment.TAG);
        if(fragment != null) {
            fragment.refresh();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        PersonListFragment fragment = (PersonListFragment)getSupportFragmentManager()
                .findFragmentByTag(PersonListFragment.TAG);
        if(fragment != null) {
            fragment.refresh();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_new) {
            Intent intent = new Intent(this, FormActivity.class);
            startActivityForResult(intent, REQUEST_FORM);
        }
        return true;
    }

    @Override
    public void personClicked(Person p) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_PERSON, p);
        startActivityForResult(intent, REQUEST_DETAILS);
    }
}