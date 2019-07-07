package com.phungthanhquan.bookapp.Model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;

import java.util.List;

@Dao
public interface UserRentDao {
    @Query("SELECT * FROM userrent")
    List<UserRent> getALl();

    @Query("SELECT * FROM UserRent WHERE user_id LIKE :user_id LIMIT 1")
    UserRent findByID(String user_id);

    @Insert
    void insertAll(UserRent... userRents);

    @Update
    void update(UserRent userRent);

    @Delete
    void delete(UserRent userRent);

    @Insert
    void insert(UserRent book);

    @Query("DELETE FROM userrent")
    public void deleteAll();
}
