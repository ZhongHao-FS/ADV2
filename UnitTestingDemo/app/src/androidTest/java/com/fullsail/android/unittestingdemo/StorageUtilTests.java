package com.fullsail.android.unittestingdemo;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import com.fullsail.android.unittestingdemo.data.Person;
import com.fullsail.android.unittestingdemo.util.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StorageUtilTests {
    private static final String MOCK_FILE_LOCATION = "mockData.dat";

    @Test
    public void testPersonSaving() {
        // Get our app's context.
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Create a new person.
        Person person = new Person("John", "Doe", 30);
        // Save the person to a file.
        StorageUtil.savePerson(context, person);
        // Load the data from the file.
        ArrayList<Person> people = StorageUtil.loadPeople(context);
        // Verify the saved person exists in the loaded list.
        assertTrue(people.contains(person));
    }

    @Test
    public void testMockStoragePersonSaving() throws IOException {
        // Get our real context.
        Context realContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Create a mock context.
        Context mockContext = Mockito.mock(Context.class);
        // When using our mock context to open a file, use the real context
        // to open a different file.
        Mockito.when(mockContext.openFileInput(Mockito.anyString())).thenReturn(realContext.openFileInput(MOCK_FILE_LOCATION));
        Mockito.when(mockContext.openFileOutput(Mockito.anyString(), Mockito.anyInt())).thenReturn(realContext.openFileOutput(MOCK_FILE_LOCATION, Context.MODE_PRIVATE));

        // Create a new person.
        Person person = new Person("John", "Doe", 30);
        // Save the person to a file using the mock context.
        StorageUtil.savePerson(mockContext, person);
        // Load the data from the file using the mock context.
        ArrayList<Person> people = StorageUtil.loadPeople(mockContext);
        // Verify the saved person exists in the loaded list.
        assertTrue(people.contains(person));
    }

    private void clearMockStorage() {
        // Get our real context.
        Context realContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Create a mock context.
        Context mockContext = Mockito.mock(Context.class);
        // Change our mock implementation of deleteFile to delete our test file.
        Mockito.when(mockContext.deleteFile(Mockito.anyString())).thenReturn(realContext.deleteFile(MOCK_FILE_LOCATION));
        // Delete our mock file using our mock context.
        StorageUtil.deletePeopleFile(mockContext);
    }

    @Before
    public void setup() {
        // Clear our mock storage by deleting the file.
        clearMockStorage();
        // Get our real context.
        Context realContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // Create a file handle to the mock storage location.
        File mockStorage = new File(realContext.getFilesDir(), MOCK_FILE_LOCATION);
        // Create a blank mock storage file.
        try {
            mockStorage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        clearMockStorage();
    }
}
