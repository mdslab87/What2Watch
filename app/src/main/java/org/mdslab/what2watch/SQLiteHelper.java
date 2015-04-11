package org.mdslab.what2watch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdslab on 11/04/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "What2WatchDB";
    private static final String TABLE_TOP = "top25";
    private static final String TABLE_THEATERS = "theaters";
    private static final String TABLE_COMING = "comingsoon";

    private static final String KEY_ID = "id";
    private static final String KEY_IMAGE = "images";
    private static final String KEY_TITLE = "titles";
    private static final String KEY_YEARS = "years";
    private static final String KEY_RATING = "ratings";

    private static final String[] COLUMNS = {KEY_ID,KEY_IMAGE,KEY_TITLE,KEY_YEARS,KEY_RATING};

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE top25 (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, images varchar, titles varchar, years varchar, ratings varchar)";
        String CREATE_TABLE1 = "CREATE TABLE theaters (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, images varchar, titles varchar, years varchar, ratings varchar)";
        String CREATE_TABLE2 = "CREATE TABLE comingsoon (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, images varchar, titles varchar, years varchar, ratings varchar)";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS top25");
        db.execSQL("DROP TABLE IF EXISTS theaters");
        db.execSQL("DROP TABLE IF EXISTS comingsoon");
        this.onCreate(db);
    }

    public void addFilm(Film film, int type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_IMAGE,film.getImageURL());
        values.put(KEY_TITLE,film.getTitle());
        values.put(KEY_YEARS,film.getYear());
        values.put(KEY_RATING,film.getRating());

        switch (type) {
            case 0: {
                db.insert(TABLE_TOP, null, values);
                db.close();
                break;
            }
            case 1: {
                db.insert(TABLE_THEATERS, null, values);
                db.close();
                break;
            }
            case 2: {
                db.insert(TABLE_COMING, null, values);
                db.close();
                break;
            }
        }
    }
    public Film getFilm(int id, int type) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        switch(type){
            case 0: {
                cursor = db.query(TABLE_TOP, COLUMNS, "id = ?", new String[] { String.valueOf(id)}, null, null, null, null);
                break;
            }
            case 1: {
                cursor = db.query(TABLE_THEATERS, COLUMNS, "id = ?", new String[] { String.valueOf(id)}, null, null, null, null);
                break;
            }
            case 2: {
                cursor = db.query(TABLE_COMING, COLUMNS, "id = ?", new String[] { String.valueOf(id)}, null, null, null, null);
                break;
            }
        }
        if (cursor!=null){
            cursor.moveToFirst();
        }

        Film film = new Film();

        film.setId(Integer.parseInt(cursor.getString(0)));
        film.setImageURL(cursor.getString(1));
        film.setTitle(cursor.getString(2));
        film.setYear(cursor.getString(3));
        film.setRating(cursor.getString(4));

        return film;
    }

    public List<Film> getAllFilm(int type) {
        List<Film> listfilm = new ArrayList<Film>();

        String query = null;

        switch (type) {
            case 0: {
                query = "SELECT * FROM " + TABLE_TOP;
                break;
            }
            case 1: {
                query = "SELECT * FROM " + TABLE_THEATERS;
                break;
            }
            case 2: {
                query = "SELECT * FROM " + TABLE_COMING;
                break;
            }
        }
        if(query != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            Film film = null;
            if (cursor.moveToFirst()){
                do {
                    film = new Film();

                    film.setId(Integer.parseInt(cursor.getString(0)));
                    film.setImageURL(cursor.getString(1));
                    film.setTitle(cursor.getString(2));
                    film.setYear(cursor.getString(3));
                    film.setRating(cursor.getString(4));

                    listfilm.add(film);

                } while (cursor.moveToNext());
            }
            return listfilm;
        }
        return null;
    }

    public int updateFilm(Film film, int type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_IMAGE,film.getImageURL());
        values.put(KEY_TITLE,film.getTitle());
        values.put(KEY_YEARS,film.getYear());
        values.put(KEY_RATING,film.getRating());

        int index = -1;

        switch(type) {
            case 0: {
                index = db.update(TABLE_TOP, values, KEY_ID+" = ?", new String[] { String.valueOf(film.getId())});
                db.close();
                break;
            }
            case 1: {
                index = db.update(TABLE_THEATERS, values, KEY_ID+" = ?", new String[] { String.valueOf(film.getId())});
                db.close();
                break;
            }
            case 2: {
                index = db.update(TABLE_COMING, values, KEY_ID+" = ?", new String[] { String.valueOf(film.getId())});
                db.close();
                break;
            }
        }
        return index;
    }
    public void deleteFilm(Film film, int type) {
        SQLiteDatabase db = this.getWritableDatabase();
        switch (type){
            case 0: {
                db.delete(TABLE_TOP, KEY_ID+" = ?", new String[] {String.valueOf(film)});
                db.close();
                break;
            }
            case 1: {
                db.delete(TABLE_THEATERS, KEY_ID+" = ?", new String[] {String.valueOf(film)});
                db.close();
                break;
            }
            case 2: {
                db.delete(TABLE_COMING, KEY_ID+" = ?", new String[] {String.valueOf(film)});
                db.close();
                break;
            }
        }
    }
}
