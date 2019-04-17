package com.kalistdev.breathtraining;

/**
 * Breath Training Application
 *
 * This file is part of the Breath Training package.
 * This class enum DataBase field.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public enum DataBaseField {

    /** DataBase name. */
    DATABASE_NAME("training_db.db"),

    /** DataBase table name. */
    DATABASE_TABLE("training_list"),

    /** DataBase version. */
    DATABASE_VERSION("1"),

    /** Field <p>id</p> int the DataBase. */
    KEY_ID("_id", 0),

    /** Field <p>name</p> int the DataBase. */
    KEY_NAME_TRAINING("name", 1),

    /** Field <p>inhale</p> int the DataBase. */
    KEY_INHALE("inhale", 2),

    /** Field <p>exhale</p> int the DataBase. */
    KEY_EXHALE("exhale", 3),

    /** Field <p>mPauseAfterInhale</p> int the DataBase. */
    KEY_PAUSE_AFTER_INHALE("mPauseAfterInhale", 4),

    /** Field <p>mPauseAfterExhale</p> int the DataBase. */
    KEY_PAUSE_AFTER_EXHALE("mPauseAfterExhale", 5),

    /** Field <p>time</p> int the DataBase. */
    KEY_TIME("time", 6),

    /** DataBase creation. */
    DATABASE_CREATE("create table " + DATABASE_TABLE.getValue() + " ("
            + KEY_ID.getValue() + " integer primary key autoincrement, "
            + KEY_NAME_TRAINING.getValue() + " text, "
            + KEY_INHALE.getValue() + " integer, "
            + KEY_EXHALE.getValue() + " integer, "
            + KEY_PAUSE_AFTER_INHALE.getValue() + " integer, "
            + KEY_PAUSE_AFTER_EXHALE.getValue() + " integer, "
            + KEY_TIME.getValue() + " integer)");

    /** Element value. */
    private String mValue;

    /** Unique element id. */
    private int mId;

    /**
     * Constructor - create a new object with full initialization.
     *
     * @param value - element value.
     */
    DataBaseField(final String value) {
        mValue = value;
    }

    /**
     * Constructor - create a new object with full initialization.
     *
     * @param value -  element value.
     * @param id - unique element id.
     */
    DataBaseField(final String value, final int id) {
        mValue = value;
        mId = id;
    }

    /**
     * Function to get value of field {@link DataBaseField#mValue}.
     * @return element value.
     */
    public String getValue() {
        return mValue;
    }

    /**
     * Function to get value of field {@link DataBaseField#mId}.
     * @return element id.
     */
    public int getId() {
        return mId;
    }
}
