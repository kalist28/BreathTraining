package com.kalistdev.breathtraining.main;

import java.sql.SQLDataException;
import java.util.Objects;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.MenuItem;
import com.kalistdev.breathtraining.R;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.kalistdev.breathtraining.Record.Training;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * This class main.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {


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
                case R.id.navigation_home: {
                    openFragment(new ListTrainingFragment());
                    return true;
                }

                case R.id.navigation_information: {
                    openFragment(new AppInfoFragment());
                    return true;
                }

                case R.id.navigation_journal: {
                    openFragment(new JournalFragment());
                    openOptionsMenu();
                    return true;
                }

                default:
                    return false;
            }
        }
    };

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar())
                .setDisplayHomeAsUpEnabled(false);

        checkNull();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation
                .setOnNavigationItemSelectedListener(mOnNavigationItemListener);
        openFragment(new ListTrainingFragment());

    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.journal_deleteAll: {
                JournalFragment.clearJournal();
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }

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

    /** If the number of training = 0, a standard one is created. */
    private void checkNull() {
        final int
                inhale  = 6,
                exhale  = 5,
                pauseA  = 3,
                pauseB  = 5,
                time    = 6;

        if (Training.listAll(Training.class).size() == 0) {
            Training training
                    = new Training(
                    getString(R.string.average_level),
                    inhale,
                    exhale,
                    pauseA,
                    pauseB,
                    time);
            training.save();
        }
    }
}
