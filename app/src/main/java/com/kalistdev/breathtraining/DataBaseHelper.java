package com.kalistdev.breathtraining;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "training_db.db";
    public static final String DATABASE_TABLE = "training_list";
    public static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME_TRAINING = "name";
    public static final String KEY_INHALE = "inhale"; //Вдох
    public static final String KEY_EXHALE = "exhale"; //Выдох
    public static final String KEY_PAUSE_A = "pause_a"; //первая пауза
    public static final String KEY_PAUSE_B = "pause_b"; //вторая пауза
    public static final String KEY_TIME = "time"; //вторая пауза

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " (" +
                    KEY_ID + " integer primary key autoincrement, " +
                    KEY_NAME_TRAINING + " text, " +
                    KEY_INHALE + " integer, " +
                    KEY_EXHALE + " integer, " +
                    KEY_PAUSE_A + " integer, " +
                    KEY_PAUSE_B + " integer, " +
                    KEY_TIME + " integer)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_TABLE);
    }
}
