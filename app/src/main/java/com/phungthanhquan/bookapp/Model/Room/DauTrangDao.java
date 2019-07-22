package com.phungthanhquan.bookapp.Model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.phungthanhquan.bookapp.Object.ChuongSach;
import com.phungthanhquan.bookapp.Object.DauTrang;

import java.util.List;

@Dao
public interface DauTrangDao {

    @Query("SELECT * FROM DauTrang where book_id=:book_id ")
    List<DauTrang> getALl(String book_id);


    @Insert
    void insert(DauTrang dauTrang);

    @Query("DELETE FROM dautrang WHERE  trang= :numpage")
    void delete(int numpage);
}
