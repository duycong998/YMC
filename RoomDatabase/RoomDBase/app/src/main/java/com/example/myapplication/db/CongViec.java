package com.example.myapplication.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity (tableName = "cong_viec")
public class CongViec {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cvId")
    private long cvID;

    @ColumnInfo(name = "tenCV")
    private String tenCV;

    public CongViec( String tenCV) {
      //  this.cvID = cvID;
        this.tenCV = tenCV;
    }



    public long getCvID() {
        return cvID;
    }

    public void setCvID(long cvID) {
        this.cvID = cvID;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;

    }

    //    public String getTenCV(){
//        return tenCV;
//    }
//
//    public void setTenCV(String tenCV){
//        this.tenCV = tenCV;
//    }
}
