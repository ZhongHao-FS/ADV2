package com.fullsail.android.adv2.zhonghao_ce07;

import java.util.Random;

public class Item {
    private final int mValue;
    private final int mX;
    private final int mY;

    public Item(int _value) {
        mValue = _value;

        Random rand = new Random();
        mX = rand.nextInt(DrawingSurface.width);
        mY = rand.nextInt(DrawingSurface.height);
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
