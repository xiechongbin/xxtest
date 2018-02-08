package com.example.tcpdemo;

import android.util.Log;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcb on 2018/1/26.
 * 文件解析工具类
 */

public class FileParseUtils {

    /**
     * 逐行解析txt文件
     */
    public static void parseTxtFile(File file) {
        if (file == null) {
            return;
        }
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            final List<BlackListTable> tableList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(",");
                if (array.length < 3) {
                    Log.d("FileParseUtils", line);
                    continue;
                }
                Log.d("FileParseUtils", line);
                BlackListTable table = new BlackListTable();
                String plate = array[0];
                String hphm = array[1];
                String hpzl = array[2];
                table.setCarPlate(plate);
                table.setCarColor(hphm);
                table.setCarType(hpzl);
                tableList.add(table);
            }
            DataSupport.saveAllAsync(tableList).listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                    if (success) {
                        Log.d("FileParseUtils", "插入成功" + tableList.size());
                        tableList.clear();
                    }

                }
            });
        } catch (FileNotFoundException e) {
            Log.d("FileParseUtils", "" + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("FileParseUtils", "" + e);
            e.printStackTrace();
        }
    }
}
