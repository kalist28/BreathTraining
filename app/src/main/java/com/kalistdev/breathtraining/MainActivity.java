package com.kalistdev.breathtraining;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Breath Training Application
 *
 * This file is part of the Breath Training package.
 * This class main.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    /** Object SQL. */
    private SQLiteDatabase sqLiteDatabase;

    /**
     * DataBaseHelper object.
     * @see DataBaseHelper
     */
    private DataBaseHelper dataBaseHelper;

    /**
     * Lower navigation panel.
     * It serves to change fragments.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ContentValues cv = new ContentValues();
                    cv.put(DataBaseField.KEY_NAME_TRAINING.getValue(), "123");
                    sqLiteDatabase
                            .insert(DataBaseField.DATABASE_TABLE.getValue(),
                                        null, cv);
                    return true;

                case R.id.navigation_dashboard:
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                            + DataBaseField.DATABASE_TABLE.getValue(), null);
                    cursor.moveToFirst();
                    int count = 0;
                    while (!cursor.isAfterLast()) {
                        Log.d("Test ", count++ + "");
                        cursor.moveToNext();
                    }
                    cursor.close();
                    return true;

                case R.id.navigation_notifications:
                    openFragment(new ListFragment());
                    return true;

                default:
                    return false;
            }
        }
    };

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        //mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation
            .setOnNavigationItemSelectedListener(mOnNavigationItemListener);
    }

    /**
     * This functions changes the fragments on the main screen.
     * @param fragment - a fragment that you need to show.
     */
    private void openFragment(final Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl, fragment);
        fragmentTransaction.commit();
    }
}
