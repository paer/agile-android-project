package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;

/**
 * Created by darwish on 2015-05-03.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VESRION = 0;
    private static final String DATABASE_NAME = "test_db";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESRION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("SELECT FROM ");
    }
}
