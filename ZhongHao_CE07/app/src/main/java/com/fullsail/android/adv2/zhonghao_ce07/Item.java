package com.fullsail.android.adv2.zhonghao_ce07;

import java.util.Random;

public class Item {
    private String mName;
    private int mValue;
    private int mX;
    private int mY;

    public Item(String _name, int _value) {
        mName = _name;
        mValue = _value;

        Random rand = new Random();
        mX = rand.nextInt(DrawingSurface.width);
        mY = rand.nextInt(DrawingSurface.height);
    }

    public String getName() {
        return mName;
    }

    public int getValue() {
        return mValue;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }
}
