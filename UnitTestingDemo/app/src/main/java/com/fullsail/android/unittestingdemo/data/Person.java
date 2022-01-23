package com.fullsail.android.unittestingdemo.data;

import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
    public String firstName;
    public String lastName;
    public int age;

    public Person() {
        firstName = lastName = "";
        age = -1;
    }

    public Person(String _first, String _last, int _age) {
        firstName = _first;
        lastName = _last;
        age = _age;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Person)) {
            return false;
        }

        Person other = (Person)obj;

        return Objects.equals(other.firstName, firstName) &&
                Objects.equals(other.lastName, lastName) &&
                other.age == age;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + age;
    }
}
