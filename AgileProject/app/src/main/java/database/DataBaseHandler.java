package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import database.DBMetaData.User;
import database.DBMetaData.User_note;

import java.sql.SQLException;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VESRION = 1;
    public String CREATE_USER_TABLE = "CREATE TABLE " + User.USER_TABLE + "(" + DBMetaData.USER_ID + " INTEGER PRIMARY KEY ASC , " + User.USER_NAME + " TEXT," + User.USER_PASSWORD + " TEXT);";
    public String CREATE_NOTE_TABLE = "CREATE TABLE " + User_note.NOTE_TABLE + "(" + User_note.NOTE_ID + " INTEGER PRIMARY KEY ASC," + DBMetaData.USER_ID + " INTEGER REFERENCES USER(USER_ID) , " + User_note.NOTE_TEXT + " TEXT);";


    public DataBaseHandler(Context context) {
        super(context, DBMetaData.DATABASE_NAME, null, DATABASE_VESRION);
        Log.d("Database operations", "DB Created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USER_TABLE);
        Log.d("Database operations", "TABLE USER CRETED");
        database.execSQL(CREATE_NOTE_TABLE);
        Log.d("Database operations", "TABLE NOTE CRETED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldV, int newV) {

    }

    public void insertUser(DataBaseHandler dbh, String name, String password) {
        SQLiteDatabase SQ = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User.USER_NAME, name);
        cv.put(User.USER_PASSWORD, password);
        long retVal = SQ.insert(User.USER_TABLE, null, cv);
        Log.d("Database operations", "A user raw inserted");
    }

    public void insertNote(DataBaseHandler dbh, int user_id, String note_text) {
        SQLiteDatabase SQ = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBMetaData.USER_ID, user_id);
        cv.put(User_note.NOTE_TEXT, note_text);
        long retVal = SQ.insert(User_note.NOTE_TABLE, null, cv);
        Log.d("Database operations", "A note raw inserted");
    }

    public Cursor selectUser(int user_id) throws SQLException {
        String[] columns = {User.USER_NAME, User.USER_PASSWORD};
        SQLiteDatabase SQ = this.getWritableDatabase();
        return SQ.query(User.USER_TABLE, columns, DBMetaData.USER_ID + "=" + user_id, null, null, null, null);
    }

    public Cursor selectUserNotes(int user_id) throws SQLException {
        String[] columns = {User_note.NOTE_ID, User_note.NOTE_TEXT};
        SQLiteDatabase SQ = this.getWritableDatabase();
        return SQ.query(User_note.NOTE_TABLE, columns, DBMetaData.USER_ID + "=" + user_id, null, null, null, null);
    }


}
