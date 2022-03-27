package com.example.memoapp.DB;

public class MemoData extends BaseData {

    private String TITLE = "";
    private String TEXT = "";

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getTEXT() {
        return TEXT;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }

}
