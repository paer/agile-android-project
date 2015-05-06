package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import database.DBMetaData.MetaUserNote;
import database.DBMetaData.MetaUser;



import java.sql.SQLException;

/**
 * Created by darwish on 2015-05-03.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VESRION = 1;
    public String CREATE_USER_TABLE = "CREATE TABLE " + MetaUser.USER_TABLE + "(" + DBMetaData.USER_ID + " INTEGER PRIMARY KEY ASC , " + MetaUser.USER_NAME + " TEXT," + MetaUser.USER_PASSWORD + " TEXT);";
    public String CREATE_NOTE_TABLE = "CREATE TABLE " + MetaUserNote.NOTE_TABLE + "(" + MetaUserNote.NOTE_ID + " INTEGER PRIMARY KEY ASC," + DBMetaData.USER_ID + " INTEGER REFERENCES USER(USER_ID) , " + MetaUserNote.NOTE_TEXT + " TEXT);";

    private Context context;
    private SQLiteDatabase SQ;
    private DataBaseHandler handler;


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
        ContentValues cv = new ContentValues();
        cv.put(MetaUser.USER_NAME, name);
        cv.put(MetaUser.USER_PASSWORD, password);
        long retVal = SQ.insert(MetaUser.USER_TABLE, null, cv);
        Log.d("Database operations", "A user raw inserted");
    }

    public void insertNote(DataBaseHandler dbh, int user_id, String note_text) {
        ContentValues cv = new ContentValues();
        cv.put(DBMetaData.USER_ID, user_id);
        cv.put(MetaUserNote.NOTE_TEXT, note_text);
        long retVal = SQ.insert(MetaUserNote.NOTE_TABLE, null, cv);
        Log.d("Database operations", "A note raw inserted");
    }

    public Cursor selectUser(int user_id) throws SQLException {
        String[] columns = {DBMetaData.USER_ID,MetaUser.USER_NAME, MetaUser.USER_PASSWORD};
        return SQ.query(MetaUser.USER_TABLE, columns, DBMetaData.USER_ID + "=" + user_id, null, null, null, null);
    }

    public Cursor selectUserNotes(int user_id) throws SQLException {
        String[] columns = {MetaUserNote.NOTE_ID, MetaUserNote.NOTE_TEXT};
        return SQ.query(MetaUserNote.NOTE_TABLE, columns, DBMetaData.USER_ID + "=" + user_id, null, null, null, null);
    }

    public boolean updateUser(int user_id, String user_name, String user_pass){
        ContentValues cv = new ContentValues();
        cv.put(MetaUser.USER_NAME, user_name);
        cv.put(MetaUser.USER_PASSWORD, user_pass);
        return SQ.update(MetaUser.USER_TABLE, cv, DBMetaData.USER_ID + "=" + user_id, null) > 0;
    }

    public boolean updateUserNote(int note_id, String note_text){
        ContentValues cv = new ContentValues();
        cv.put(MetaUserNote.NOTE_TEXT, note_text);
        return SQ.update(MetaUserNote.NOTE_TABLE, cv, MetaUserNote.NOTE_ID + "=" + note_id, null) > 0;
    }

    /**
     * DELETE A RECORD
     */
    public boolean deleteNote(int note_id){
        return SQ.delete(MetaUserNote.NOTE_TABLE, MetaUserNote.NOTE_ID + "=" + note_id, null) > 0;
    }

    public DataBaseHandler open(Context context) throws SQLException{
        handler = new DataBaseHandler(context);
        SQ = handler.getWritableDatabase();
        return this;
    }

    /**
     *  CLOSE THE CONNECTION TO THE DB
     */
    public void close() {
        if (handler != null)
            handler.close();
    }

}
