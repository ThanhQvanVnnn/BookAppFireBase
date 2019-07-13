package com.phungthanhquan.bookapp.Model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;

import java.util.List;

@Dao
public interface BookCaseDao {
    @Query("SELECT * FROM bookcase")
    List<BookCase> getALl();

    @Query("SELECT * FROM BookCase WHERE book_id LIKE :id LIMIT 1")
    BookCase findByID(String id);

    @Insert
    void insertAll(BookCase... books);

    @Insert
    void insert(BookCase book);

    @Delete
    void delete(BookCase book);

    @Update
    void update(BookCase bookCase);

    @Query("DELETE FROM bookcase")
    public void deleteAll();
}
