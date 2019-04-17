package com.kalistdev.breathtraining;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Breath Training Application
 *
 * This file is part of the Breath Training package.
 * This class assistant for work SQL DataBase.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Constructor - create a new object.
     * @param context application context.
     */
    DataBaseHelper(final Context context) {
        super(context, DataBaseField.DATABASE_NAME.getValue(),
                null,
                Integer.valueOf(DataBaseField.DATABASE_VERSION.getValue()));
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {
        db.execSQL(DataBaseField.DATABASE_CREATE.getValue());
    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion) {
        db.execSQL("drop table if exists "
                + DataBaseField.DATABASE_TABLE.getValue());
    }
}
