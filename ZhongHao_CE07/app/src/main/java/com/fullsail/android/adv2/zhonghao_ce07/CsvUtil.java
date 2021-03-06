package com.fullsail.android.adv2.zhonghao_ce07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CsvUtil {
    final InputStream inputStream;

    public CsvUtil(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public ArrayList<Item> read() {
        ArrayList<Item> itemList = new ArrayList();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                Item item = new Item(Integer.parseInt(row[1]));
                itemList.add(item);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemList;
    }
}
