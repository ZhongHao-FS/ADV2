package com.fullsail.android.unittestingdemo.util;

import android.content.Context;

import com.fullsail.android.unittestingdemo.data.Person;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageUtil {
    private static final String FILE_NAME = "storage.dat";

    public static void savePerson(Context context, Person person) {
        ArrayList<Person> people = loadPeople(context);
        people.add(person);
        savePeople(context, people);
    }

    public static ArrayList<Person> loadPeople(Context context) {

        ArrayList<Person> people = null;

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            people = (ArrayList<Person>)ois.readObject();
            ois.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(people == null) {
            people = new ArrayList<>();
        }

        return people;
    }

    public static void deletePerson(Context context, Person person) {
        ArrayList<Person> people = loadPeople(context);
        while(people.remove(person));
        savePeople(context, people);
    }

    private static void savePeople(Context context, ArrayList<Person> people) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(people);
            oos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletePeopleFile(Context context) {
        context.deleteFile(FILE_NAME);
    }
}
