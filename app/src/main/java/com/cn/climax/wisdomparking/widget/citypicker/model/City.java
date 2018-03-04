package com.cn.climax.wisdomparking.widget.citypicker.model;

/**
 * author：leo on 2017/3/17 13:36
 * email： leocheung4ever@gmail.com
 * description: 城市对象
 * what & why is modified:
 */

public class City {

    private String name;
    private String pinyin;

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
