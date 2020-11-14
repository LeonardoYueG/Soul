package com.test.framework.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.test.framework.BuildConfig;

//使用SharedPreferences的方式来存储数据,存的是键值对
public class SpUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private volatile static SpUtils mSpUtils = null;

    private SpUtils() {
    }

    public static SpUtils getInstance() {
        if (mSpUtils == null) {
            synchronized (SpUtils.class) {
                if (mSpUtils == null) {
                    mSpUtils = new SpUtils();
                }
            }
        }
        return mSpUtils;
    }

    //init SP
    public void initSp(Context mContext) {
        sp = mContext.getSharedPreferences(BuildConfig.SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void putInt(String key, int values) {
        editor.putInt(key, values);
        editor.commit();
    }

    public int getInt(String key, int defValues) {
        return sp.getInt(key, defValues);
    }

    public void putString(String key, String values) {
        editor.putString(key, values);
        editor.commit();
    }

    public String getString(String key, String defValues) {
        return sp.getString(key, defValues);
    }

    public void putBoolean(String key, boolean values) {
        editor.putBoolean(key, values);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValues) {
        return sp.getBoolean(key, defValues);
    }

    public void deleteKey(String key){
        editor.remove(key);
        editor.commit();
    }
}
