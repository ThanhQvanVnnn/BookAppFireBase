package com.phungthanhquan.bookapp.Model.Room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.phungthanhquan.bookapp.Object.Book;
import com.phungthanhquan.bookapp.Object.BookCase;
import com.phungthanhquan.bookapp.Object.UserRent;

import java.util.concurrent.ExecutionException;

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

    public void insertBookCaseTask(final Context context, final BookCase note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DbRoomAccess.getInstance(context).bookcaseAccess().insert(note);
                return null;
            }
        }.execute();
    }
    public void insertUserRentTask(final Context context, final UserRent note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DbRoomAccess.getInstance(context).userRentAccess().insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }



    public void deleteBookCaseTask(final Context context, final BookCase note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DbRoomAccess.getInstance(context).bookcaseAccess().delete(note);
                return null;
            }
        }.execute();
    }

    public BookCase getBookCaseByIDTask(final Context context, final String id) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, BookCase>() {
            @Override
            protected BookCase doInBackground(Void... voids) {
                return DbRoomAccess.getInstance(context).bookcaseAccess().findByID(id);
            }
        }.execute().get();
    }

    public UserRent getUserRentByIDTask(final Context context, final String id) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, UserRent>() {
            @Override
            protected UserRent doInBackground(Void... voids) {
                return DbRoomAccess.getInstance(context).userRentAccess().findByID(id);
            }
        }.execute().get();
    }

    public void deleteAllBookcaseTask(final Context context){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DbRoomAccess.getInstance(context).bookcaseAccess().deleteAll();
                return null;
            }
        }.execute();
    }
    public void deleteAllUserRentTask(final Context context){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DbRoomAccess.getInstance(context).userRentAccess().deleteAll();
                return null;
            }
        }.execute();
    }

}
