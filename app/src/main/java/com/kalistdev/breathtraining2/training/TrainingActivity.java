package com.kalistdev.breathtraining2.training;

import android.view.View;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.os.AsyncTask;
import android.widget.Toast;
import com.google.gson.Gson;
import android.view.KeyEvent;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;
import com.kalistdev.breathtraining2.R;
import android.annotation.SuppressLint;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import androidx.appcompat.app.AppCompatActivity;
import com.kalistdev.breathtraining2.Record.Training;
import com.kalistdev.breathtraining2.widget.Stopwatch;
import com.kalistdev.breathtraining2.finishing.FinishingActivity;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * The basic training class.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class TrainingActivity extends AppCompatActivity {

    /** The position toast view_element_training of Y. */
    private static final int POSITION_Y = 100;

    /** Number of seconds before training. */
    private static final int COUNTDOWN_START = 3;

    /** Responsible for displaying elapsed time. */
    private TextView mTimeInfo;

    /** Responsible for displaying stopwatch's. */
    private TextView mStopwatch;

    /** Responsible for displaying number of execution of all parameters. */
    private TextView mParamsInfo;

    /** Takes the name of the last parameter. */
    private TextView mParamOne;

    /** Takes the name of this parameter. */
    private TextView mParamTwo;

    /** Takes the name of the future parameter. */
    private TextView mParamThree;

    /** An immutable instance of the engine in training. */
    private final TrainingEngine trainingEngine = new TrainingEngine();

    /** Stores information about breathing parameters. */
    private ParametersManager parametersManager;

    /** The variable is responsible for the TrainingRecord Engine. */
    private boolean asyncWork;

    /**
     * Responsible for double-clicking the STOP button,
     * if false , the button is pressed 1 time otherwise 2 times.
     */
    private boolean boolStop;

    /** Internal variables (counters). */
    private int
            allTime,
            ordinalNumberElement;

    /** Selected training option. */
     private Training training;

    /** Name training. */
    private String mNameTraining;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        MobileAds.initialize(this, "ca-app-pub-3171253839600710~1315320324");

        boolStop = false;

        TextView textView       = findViewById(R.id.name);
        mTimeInfo               = findViewById(R.id.timeInfo);
        mStopwatch              = findViewById(R.id.stopwatch);
        mParamsInfo             = findViewById(R.id.paramsInfo);
        Button buttonStop       = findViewById(R.id.btmstop);

        mParamOne   = findViewById(R.id.param1);
        mParamTwo   = findViewById(R.id.param2);
        mParamThree = findViewById(R.id.param3);

        Gson gson = new Gson();
        training = gson.fromJson(getIntent()
                        .getStringExtra("training"), Training.class);
        allTime             = training.getTime();
        mNameTraining       = training.getName();

        textView.setText(mNameTraining);

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });

        countdownToStart();

        AdView adView = findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
    }

    /**
     * Separate thread counting down to start,
     * After 3 seconds starts the TrainingRecord Engine.
     */
    private void countdownToStart() {
        Thread initialPause = new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                int countdown = COUNTDOWN_START;
                try {
                    TimeUnit.SECONDS.sleep(1);
                    while (true) {
                        mStopwatch.setText(
                                getString(R.string.start_after_caps)
                                + countdown);

                        TimeUnit.SECONDS.sleep(1);

                        countdown--;

                        if (countdown == 0) {
                            trainingEngine.execute();
                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        setParamsInfo(
                getString(R.string.pause),
                getString(R.string.inhale_caps),
                getString(R.string.pause));
        initialPause.start();
    }

    /**
     * Setting information in the user interface about the active parameter.
     * @param id - the ID of the active parameter.
     */
    private void setParamInfo(final int id) {
        switch (id) {
            case ParametersManager.PARAMETER_PAUSE_AFTER_INHALE:
                setParamsInfo(
                        getString(R.string.inhale),
                        getString(R.string.pause),
                        getString(R.string.exhale));
                break;
            case ParametersManager.PARAMETER_EXHALE:
                setParamsInfo(
                        getString(R.string.pause),
                        getString(R.string.exhale),
                        getString(R.string.pause));
                break;
            case ParametersManager.PARAMETER_PAUSE_AFTER_EXHALE:
                setParamsInfo(
                        getString(R.string.exhale),
                        getString(R.string.pause),
                        getString(R.string.inhale));
                break;
            case ParametersManager.PARAMETER_INHALE:
                setParamsInfo(
                        getString(R.string.pause),
                        getString(R.string.inhale),
                        getString(R.string.pause));
                break;
            default:
                break;
        }
    }

    /**
     * Setting the transmitted text in UI.
     *
     * @param oneParams - text of the first parameter.
     * @param twoParams - text of the second parameter.
     * @param threeParams - text of the third parameter.
     */
    private void setParamsInfo(final String oneParams,
                               final String twoParams,
                               final String threeParams) {
        mParamOne.setText(oneParams);
        mParamTwo.setText(twoParams);
        mParamThree.setText(threeParams);
    }

    @Override
    public final void onBackPressed() {
        if (!boolStop) {
            Toast toast =
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.click_to_stop),
                            Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, POSITION_Y);
            toast.show();
            boolStop = true;
        } else {
            asyncWork = false;
        }
    }

    @Override
    public final boolean onKeyDown(final int keyCode,
                                   final  KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return !asyncWork;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected final void onUserLeaveHint() {
        onBackPressed();
    }

    @Override
    protected final void onDestroy() {
        Intent intent = new Intent(
                getApplicationContext(),
                FinishingActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(parametersManager);
        intent.putExtra("parametersInfo", json);
        intent.putExtra("TName", mNameTraining);
        startActivity(intent);
        asyncWork = false;
        super.onDestroy();
    }

    /**
     * Breath TrainingRecord Application
     *
     * This file is part of the Breath TrainingRecord package.
     * The main view_element_training of the educational process (engine)
     *
     * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     * @version 1.0
     */
    @SuppressLint("StaticFieldLeak")
    final class TrainingEngine extends AsyncTask {

        /**
         * Time of operation of the vibrator
         * (milliseconds) when changing the parameter.
         */
        private static final int MILLISECOND_VIBRATOR = 700;

        /** The stopwatch other time. */
        private Stopwatch otherTime;

        /** The stopwatch a specific parameter. */
        private Stopwatch stopwatch;

        /** The stopwatch training. */
        private Stopwatch time;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            parametersManager
                    = new ParametersManager(
                            training.getInhale(),
                            training.getExhale(),
                            training.getPauseAfterInhale(),
                            training.getPauseAfterExhale());

            stopwatch   = new Stopwatch(Stopwatch.COUNTDOWN);
            time        = new Stopwatch(Stopwatch.COUNT);
            otherTime   = new Stopwatch(Stopwatch.COUNT);

            asyncWork = true;
            ordinalNumberElement = ParametersManager.PARAMETER_INHALE;
            stopwatch.setSeconds(parametersManager
                        .getParameterId(ordinalNumberElement)
                        .getOperatingTime());
        }

        @Override
        protected Object doInBackground(final Object[] objects) {
            final int min = 60;
            while (asyncWork) {
                try {
                    publishProgress();
                    TimeUnit.SECONDS.sleep(1);
                    time.goStep();
                    stopwatch.goStep();
                    otherTime.setSeconds(allTime * min - time.getSeconds());
                    setStopWatch();

                    if (otherTime.getSeconds() <= 0) {
                        asyncWork = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            parametersManager.setTotalTime(time.getSeconds() - 1);
            finish();
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(final Object[] values) {
            super.onProgressUpdate(values);

            setParamInfo(ordinalNumberElement);
            mStopwatch.setText(stopwatch.getTime());

            mTimeInfo.setText(
                getString(R.string.Training_time) + allTime
                + getString(R.string.minutes) + "\n"
                + getString(R.string.passed)    + time.getTime() + "\n"
                + getString(R.string.remained)  + otherTime.getTime());

            int countPauses =
                    parametersManager
                            .getParameterId(
                                    ParametersManager
                                            .PARAMETER_PAUSE_AFTER_INHALE)
                                                .getNumberExecutions()
                    + parametersManager
                            .getParameterId(
                                    ParametersManager
                                            .PARAMETER_PAUSE_AFTER_EXHALE)
                                                .getNumberExecutions();
            mParamsInfo.setText(
                    getString(R.string.inhales)
                    + parametersManager
                            .getParameterId(
                                    ParametersManager.PARAMETER_INHALE)
                                        .getNumberExecutions() + " \n"
                    + getString(R.string.exhales) + parametersManager
                            .getParameterId(
                                    ParametersManager.PARAMETER_EXHALE)
                                        .getNumberExecutions() + " \n"
                    + getString(R.string.pauses) + countPauses);
        }

        /** Sets the counter for the desired parameter. */
        private void setStopWatch() {
            if (stopwatch.getSeconds() == -1) {
                parametersManager
                        .getParameterId(ordinalNumberElement)
                        .setNumberExecutions(1);

                ordinalNumberElement
                        = ordinalNumberElement == ParametersManager
                                                    .COUNT_PARAMETERS - 1
                        ? 0
                        : ordinalNumberElement + 1;

                stopwatch.setSeconds(parametersManager
                        .getParameterId(ordinalNumberElement)
                        .getOperatingTime());

                Vibrator vibrator = (Vibrator)
                        getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(MILLISECOND_VIBRATOR);
            }
        }
    }
}
