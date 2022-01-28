package com.fullsail.android.unittestingdemo;

import org.junit.Test;

import static org.junit.Assert.*;

import android.widget.TextView;

import com.fullsail.android.unittestingdemo.util.StringsUtil;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StringsUtilTests {
    @Test
    public void testEmailValidity() {
        // Test valid strings first.
        assertTrue(StringsUtil.isEmailValid("hello@example.com"));
        assertTrue(StringsUtil.isEmailValid("a.a.a@a.a.a"));
        assertTrue(StringsUtil.isEmailValid("a@a.a"));

        // Test invalid strings next.
        assertFalse(StringsUtil.isEmailValid(".@.a"));
        assertFalse(StringsUtil.isEmailValid("@a.a"));
        assertFalse(StringsUtil.isEmailValid("hello.@a.a"));
        assertFalse(StringsUtil.isEmailValid("!@#*.*&"));
    }
}