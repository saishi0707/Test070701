package com.example.memoapp.DB;

/**
 * 各データのベースとなるデータ
 */
public class BaseData {

    private String KEY = "";
    private String DATE = "";
    private String GENRE = "";

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public String getKEY() {
        return KEY;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getGENRE() {
        return GENRE;
    }

    public void setGENRE(String GENRE) {
        this.GENRE = GENRE;
    }

}