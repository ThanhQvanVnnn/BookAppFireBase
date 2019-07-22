package com.phungthanhquan.bookapp.Model.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.phungthanhquan.bookapp.Object.ChuongSach;
import java.util.List;
@Dao
public interface ChapterDao {
    @Query("SELECT * FROM chuongsach where book_id=:book_id ")
    List<ChuongSach> getALl(String book_id);


    @Insert
    void insert(ChuongSach chuongSach);
}
