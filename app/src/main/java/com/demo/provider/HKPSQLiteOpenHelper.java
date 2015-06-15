package com.demo.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.demo.BuildConfig;
import com.demo.provider.message.MessageColumns;

public class HKPSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = HKPSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "example.db";
    private static final int DATABASE_VERSION = 1;
    private static HKPSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final HKPSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_MESSAGE = "CREATE TABLE IF NOT EXISTS "
            + MessageColumns.TABLE_NAME + " ( "
            + MessageColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MessageColumns.MESSAGE_ID + " INTEGER NOT NULL, "
            + MessageColumns.MESSAGE_TEXT + " TEXT, "
            + MessageColumns.IS_SENDER_IN_PHONE_CONTACT + " TEXT, "
            + MessageColumns.SENDER + " TEXT, "
            + MessageColumns.RECEIVER + " TEXT, "
            + MessageColumns.IS_RECEIVED + " TEXT, "
            + MessageColumns.TIME + " TEXT, "
            + MessageColumns.IS_READ + " TEXT "
            + " );";

    // @formatter:on

    public static HKPSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static HKPSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static HKPSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new HKPSQLiteOpenHelper(context);
    }

    private HKPSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new HKPSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static HKPSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new HKPSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private HKPSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new HKPSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MESSAGE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
