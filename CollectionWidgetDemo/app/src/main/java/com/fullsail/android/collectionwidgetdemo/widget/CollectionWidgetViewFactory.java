package com.fullsail.android.collectionwidgetdemo.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.fullsail.android.collectionwidgetdemo.DetailsActivity;
import com.fullsail.android.collectionwidgetdemo.data.Person;
import com.fullsail.android.collectionwidgetdemo.data.PersonHelper;

import java.util.ArrayList;

public class CollectionWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Person> mPeople;

    public CollectionWidgetViewFactory(Context _context) {
        mContext = _context;
        mPeople = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        mPeople.add(new Person("Michael", "Blue", 59));
        mPeople.add(new Person("Travis", "White", 37));
        mPeople.add(new Person("Josh", "Brown", 82));
        mPeople.add(new Person("Jamie", "Black", 45));
        mPeople.add(new Person("Chris", "Green", 20));

        mPeople = PersonHelper.loadPeople(mContext);
    }

    @Override
    public void onDataSetChanged() {
        mPeople = PersonHelper.loadPeople(mContext);
    }

    @Override
    public void onDestroy() {
        mPeople.clear();
        mPeople = null;
    }

    @Override
    public int getCount() {
        return mPeople.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Person person = mPeople.get(i);
        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        itemView.setTextViewText(android.R.id.text1, person.toString());

        // Create a blank intent.
        Intent fillInIntent = new Intent();
        // Add the data to the intent.
        fillInIntent.putExtra(DetailsActivity.EXTRA_PERSON, person);
        // Attach the fill-in intent to the root of the layout.
        itemView.setOnClickFillInIntent(android.R.id.text1, fillInIntent);

        return itemView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
