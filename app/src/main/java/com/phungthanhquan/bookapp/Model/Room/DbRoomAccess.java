package com.phungthanhquan.bookapp.Model.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;

@Database(entities = {BookCase.class, UserRent.class},version = 1,exportSchema = false)
public abstract class DbRoomAccess extends RoomDatabase {
    private static final String DB_NAME = "Database.db";
    private static  DbRoomAccess instance = null;
    public static synchronized DbRoomAccess getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static DbRoomAccess create(Context context) {
        return Room.databaseBuilder(
                context,
                DbRoomAccess.class,
                DB_NAME).build();
    }

    public DbRoomAccess() {

    }
    public abstract BookCaseDao bookcaseAccess();
    public abstract UserRentDao userRentAccess();
}
