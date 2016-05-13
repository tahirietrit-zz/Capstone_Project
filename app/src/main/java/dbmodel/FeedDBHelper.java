package dbmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import objects.Item;

/**
 * Created by macb on 13/05/16.
 */
public class FeedDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Scores.db";
    private static final int DATABASE_VERSION = 1;

    public FeedDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CreateScoresTable = "CREATE TABLE IF NOT EXISTS " + Item.REPORT_TABLE + " ("
                + Item.id_ + " INTEGER PRIMARY KEY,"
                + Item.title_ + " TEXT NOT NULL,"
                + Item.description_ + " TEXT NOT NULL,"
                + Item.image_ + " TEXT NOT NULL,"
                + Item.created_by_ + " TEXT NOT NULL,"
                + Item.created_at_ + " REAL NOT NULL,"
                + Item.updated_at_ + " REAL NOT NULL,"
                + " UNIQUE (" + Item.id_ + ") ON CONFLICT REPLACE"
                + " );";
        db.execSQL(CreateScoresTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + Item.REPORT_TABLE);
    }
}
