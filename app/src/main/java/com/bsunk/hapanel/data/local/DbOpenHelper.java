package com.bsunk.hapanel.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

/**
 * Created by Bryan on 3/8/2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ha_panel_app.db";
    public static final int DATABASE_VERSION = 1;

    @Inject
    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_HA_PANEL_TABLE = "CREATE TABLE " + DatabaseContract.HAPanel.TABLE_NAME + " (" +
                DatabaseContract.HAPanel.COLUMN_ID + " INTEGER PRIMARY KEY," +
                DatabaseContract.HAPanel.COLUMN_ENTITY_ID + " TEXT NOT NULL," +
                DatabaseContract.HAPanel.COLUMN_TYPE + " TEXT," +
                DatabaseContract.HAPanel.COLUMN_STATE + " TEXT," +
                DatabaseContract.HAPanel.COLUMN_LAST_CHANGED + " TEXT," +
                DatabaseContract.HAPanel.COLUMN_ATTRIBUTES + " TEXT," +
                DatabaseContract.HAPanel.COLUMN_SHOW_FLAG + " BOOLEAN," +
                DatabaseContract.HAPanel.COLUMN_POSITION + " INTEGER" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_HA_PANEL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}