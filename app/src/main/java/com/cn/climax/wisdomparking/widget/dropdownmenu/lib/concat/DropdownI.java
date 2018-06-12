package com.cn.climax.wisdomparking.widget.dropdownmenu.lib.concat;

import android.view.View;

import com.cn.climax.wisdomparking.widget.dropdownmenu.lib.pojo.DropdownItemObject;

public interface DropdownI {
    interface SingleRow {
        void onSingleChanged(DropdownItemObject singleRowObject);//单列点击事件
    }
    interface DoubleRow {
        void onDoubleSingleChanged(DropdownItemObject singleRowObject);//单列点击事件
        void onDoubleChanged(DropdownItemObject doubleRowObject);//双列点击事件
    }
    interface ThreeRow {
        void onThreeSingleChanged(DropdownItemObject singleRowObject);//单列点击事件
        void onThreeDoubleChanged(DropdownItemObject doubleRowObject);//双列点击事件
        void onThreeChanged(DropdownItemObject threeRowObject);//三列点击事件
    }

    interface RandomView{
        void onRandom(View view);//任意view
    }
}
