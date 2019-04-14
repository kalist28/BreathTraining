package com.kalistdev.breathtraining;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private SQLiteDatabase sqLiteDatabase;
    private DataBaseHelper dataBaseHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ContentValues cv = new ContentValues();
                    cv.put(DataBaseHelper.KEY_NAME_TRAINING, "123");
                    sqLiteDatabase.insert(DataBaseHelper.DATABASE_TABLE, null, cv);
                    return true;
                case R.id.navigation_dashboard:
                    Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DataBaseHelper.DATABASE_TABLE, null);
                    cursor.moveToFirst();
                    int count = 0;
                    while (!cursor.isAfterLast()){
                        Log.d("Test ", count++ + "");
                        cursor.moveToNext();
                    }
                    cursor.close();
                    return true;
                case R.id.navigation_notifications:
                    OpenFragment(new ListFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation =findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void OpenFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl, fragment);
        fragmentTransaction.commit();
    }
}
