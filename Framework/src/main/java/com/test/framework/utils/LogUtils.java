package com.test.framework.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.test.framework.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * print and save log
 **/

public class LogUtils {

    //time
    private static SimpleDateFormat mSimpleDateFormat =
            new SimpleDateFormat("yyyt-MM-dd HH:mm:ss");


    //LogUtils.i("info");
    //print log
    public static void i(String text) {
        if (BuildConfig.LOG_DEGUB) {
            if (!TextUtils.isEmpty(text)) {
                Log.i(BuildConfig.LOG_TAG, text);
                wirteToFile(text);
            }

        }
    }

    public static void e(String text) {
        if (BuildConfig.LOG_DEGUB) {
            if (!TextUtils.isEmpty(text)) {
                Log.i(BuildConfig.LOG_TAG, text);
                wirteToFile(text);
            }

        }
    }

    public static void wirteToFile(String text) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            //file path
            String filePath = Environment.getExternalStorageDirectory().getPath() + "/Soul/";
            String fileName = "Soul.log";
            //info = time + text
            String info = mSimpleDateFormat.format(new Date()) + " " + text + "\n";
            //check the root path
            File fileRoot = new File(filePath);
            if (!fileRoot.exists()) {
                fileRoot.mkdir();
            }
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                file.mkdir();
            }
            fileOutputStream = new FileOutputStream(filePath + fileName, true);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, Charset.forName("gkb")));
            bufferedWriter.write(info);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            e(e.toString());
        }catch (IOException e){
            e.printStackTrace();
            e(e.toString());
        }
        finally {
            if (bufferedWriter!=null){
                try{
                   bufferedWriter.close();
                   fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
