package database;

import android.provider.BaseColumns;

/**
 * Created by Mazen on 5/3/2015.
 */
public class DBMetaData {
    public static final String USER_ID = "user_id";
    public static final String DATABASE_NAME = "agile_db";

    public static abstract class User implements BaseColumns {
        public static final String USER_NAME = "user_name";
        public static final String USER_PASSWORD = "user_pass";
        public static final String USER_TABLE = "user";
    }

    public static abstract class User_note implements BaseColumns {
        public static final String NOTE_TEXT = "note_text";
        public static final String NOTE_ID = "note_id";
        public static final String NOTE_TABLE = "note";
    }
}