package com.test.framework.base;

import android.os.Bundle;

import com.test.framework.utils.SystemUI;

public class baseUIActivity extends baseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUI.fixSystemUI(this);
    }
}
