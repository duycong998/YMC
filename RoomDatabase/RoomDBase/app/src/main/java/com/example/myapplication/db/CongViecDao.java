package com.example.myapplication.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface CongViecDao {
    @Insert(onConflict = REPLACE)
    void insert(CongViec ... congViec);

    @Update
  void updateCV(CongViec ... congViec);

    @Delete
    void deleteCV(CongViec congViec);

    @Query("SELECT * FROM cong_viec")
    List<CongViec> getAlphabetizedWords();


}
