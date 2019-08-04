package com.kalistdev.breathtraining.finishing;

import java.util.Date;
import android.view.Menu;
import android.os.Bundle;
import com.google.gson.Gson;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.kalistdev.breathtraining.R;
import androidx.appcompat.app.AppCompatActivity;
import com.kalistdev.breathtraining.Record.Journal;
import com.kalistdev.breathtraining.widget.StarsManager;
import com.kalistdev.breathtraining.training.ParametersManager;
import com.kalistdev.breathtraining.widget.Stopwatch;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * An activity class that displays and collects post-workout data.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class FinishingActivity extends AppCompatActivity {

    /** Parameters manager. */
    private ParametersManager parametersManager;

    @Override
    protected final void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishing);

        parametersManager
                = new Gson().fromJson(getIntent()
                        .getStringExtra("parametersInfo"),
                                    ParametersManager.class);

        setInfoOnViews();
        StarsManager manager    = findViewById(R.id.StarsManager);
        final int size = 30, margin = 5;
        manager.setSizeStar(size, margin);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveTraining();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Set all info on layout. */
    private void setInfoOnViews() {
        int countInhale = parametersManager
                .getParameterId(ParametersManager.PARAMETER_INHALE)
                .getNumberExecutions();
        int countExhale = parametersManager
                .getParameterId(ParametersManager.PARAMETER_EXHALE)
                .getNumberExecutions();
        int countPauseAfterInhale = parametersManager
                .getParameterId(ParametersManager.PARAMETER_PAUSE_AFTER_INHALE)
                .getNumberExecutions();
        int countPauseAfterExhale = parametersManager
                .getParameterId(ParametersManager.PARAMETER_PAUSE_AFTER_EXHALE)
                .getNumberExecutions();

        setNameTraining(getIntent().getStringExtra("TName"));
        setTotalTimeInfo(parametersManager.getTotalTime());
        setParametersInfo(countInhale,
                countExhale,
                countPauseAfterInhale,
                countPauseAfterExhale);
    }

    /** Set name training on layout.
     *
     * @param name - name training.
     */
    private void setNameTraining(final String name) {
        TextView textView = findViewById(R.id.activity_finishing_name_training);
        textView.setText(name);
    }

    /** Set parameters info on layout.
     *
     * @param inhale            - inhale.
     * @param exhale            - exhale.
     * @param pauseAfterInhale  - pause after inhale.
     * @param pauseAfterExhale  - pause after exhale.
     */
    private void setParametersInfo(final int inhale,
                                   final int exhale,
                                   final int pauseAfterInhale,
                                   final int pauseAfterExhale) {
        TextView textView
                = findViewById(R.id.activity_finishing_parameters_info);
        String info
                = getString(R.string.inhales) + " " + inhale + "\n"
                + getString(R.string.exhales) + " " + exhale + "\n"
                + getString(R.string.pauses) + " "
                + (pauseAfterInhale + pauseAfterExhale);
        textView.setText(info);
    }

    /** Set total time on layout.
     *
     * @param seconds - total time in seconds.
     */
    private void setTotalTimeInfo(final int seconds) {
        TextView textView = findViewById(R.id.activity_finishing_time_info);
        textView.setText("Сегодня ты тренеровался: "
                         + new Stopwatch(seconds).getTime());
    }

    /** Save new training. */
    private void saveTraining() {

        EditText editText       = findViewById(R.id.activity_finishing_opinion);
        StarsManager manager    = findViewById(R.id.StarsManager);
        Date date = new Date();

        String nameTraining     = getIntent().getStringExtra("TName");
        String parametersInfo   = getIntent().getStringExtra("parametersInfo");
        String opinion          = editText.getText().toString();
        String time             = new Gson().toJson(date);
        int countStars          = manager.getCountStars();

        Journal journal = new Journal();
        journal.initialize(nameTraining,
                           parametersInfo,
                           opinion,
                           time,
                           countStars);
        journal.save();
    }
}
