package com.kalistdev.breathtraining;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


/**
 * Breath Training Application
 *
 * This file is part of the Breath Training package.
 * The basic training class.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class TrainingActivity extends AppCompatActivity {

    /** Describes one hour in minutes. */
    private static final int ONE_MINUTE = 60;

    /** The position toast element of Y. */
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

    /** The variable is responsible for the Training Engine. */
    private boolean asyncWork;

    /**
     * Responsible for double-clicking the STOP button,
     * if false , the button is pressed 1 time otherwise 2 times.
     */
    private boolean boolStop;

    /** Internal variables (counters). */
    private int
            allTime,
            inhale,
            exhale,
            pauseAfterInhale,
            pauseAfterExhale,
            count,
            time,
            stopwatch;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        boolStop = false;

        TextView mNameTraining = findViewById(R.id.name);
        mTimeInfo = findViewById(R.id.timeInfo);
        mStopwatch = findViewById(R.id.stopwatch);
        mParamsInfo = findViewById(R.id.paramsInfo);
        Button buttonStop = findViewById(R.id.btmstop);

        mParamOne = findViewById(R.id.param1);
        mParamTwo = findViewById(R.id.param2);
        mParamThree = findViewById(R.id.param3);

        allTime = getIntent().getIntExtra("AllTime", 0);
        inhale = getIntent().getIntExtra("Inhale", 0);
        exhale = getIntent().getIntExtra("Exhale", 0);
        pauseAfterInhale = getIntent().getIntExtra("pauseAfterInhale", 0);
        pauseAfterExhale = getIntent().getIntExtra("pauseAfterExhale", 0);

        mNameTraining.setText(getIntent().getStringExtra("Name"));

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!boolStop) {
                    Toast toast =
                        Toast.makeText(getApplicationContext(),
                            "Нажмите еще для остановки.",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, POSITION_Y);
                    toast.show();
                    boolStop = true;
                } else {
                    asyncWork = false;
                }

            }
        });

        countdown();
    }

    /**
     * Separate thread counting down to start,
     * After 3 seconds starts the Training Engine.
     */
    private void countdown() {
        final TrainingEngine trainingEngine = new TrainingEngine();
        Thread initialPause = new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                int countdown = COUNTDOWN_START;
                try {
                    TimeUnit.SECONDS.sleep(1);
                    while (true) {
                        mStopwatch.setText("НАЧАЛО ЧЕРЕЗ "
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
        setParamsInfo("пауза", "ВДОХ", "пауза");
        initialPause.start();
    }

    /**
     * Breath Training Application
     *
     * This file is part of the Breath Training package.
     * The main element of the educational process (engine)
     *
     * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     * @version 1.0
     */
    @SuppressLint("StaticFieldLeak")
    final class TrainingEngine extends AsyncTask {

        /** The dimension of the array with parameters. */
        private static final int COUNT_PARAMETERS = 4;

        /** Position of the second inhale element in the array. */
        public static final int PARAMETER_INHALE = 0;

        /** Position of the thirst pause element in the array. */
        public static final int PARAMETER_PAUSE_ONE = 1;

        /** Position of the second exhale element in the array. */
        public static final int PARAMETER_EXHALE = 2;

        /** Position of the second pause element in the array. */
        public static final int PARAMETER_PAUSE_TWO = 3;

        /** Used to display the correct time. */
        private static final int CLOCK_ELEMENT = 10;

        /**
         * Time of operation of the vibrator
         * (milliseconds) when changing the parameter.
         */
        private static final int MILLISECOND_VIBRATOR = 700;

        /**
         * Array of parameters having their own values.
         * @see Parameter
         */
        private Parameter[] parameters = new Parameter[COUNT_PARAMETERS];

        /** The countdown workout. */
        private int otherTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            setParamsValue();
            asyncWork = true;
            count = 0;
            stopwatch = parameters[count].getOperatingTime();
            time = time * ONE_MINUTE;
        }

        @Override
        protected Object doInBackground(final Object[] objects) {
            while (asyncWork) {
                try {
                    setStopWatch();
                    publishProgress();
                    time += 1;
                    stopwatch--;
                    otherTime = allTime * ONE_MINUTE - time;
                    publishProgress();
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (otherTime <= 0) {
                    asyncWork = false;
                }
            }
            finish();
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(final Object[] values) {
            super.onProgressUpdate(values);

            mTimeInfo.setText(
                "Время тренировки: " + allTime + " мин.\n"
                + "Прошло: " + time / ONE_MINUTE + " : "
                + time % ONE_MINUTE + " мин.\n"
                + "Осталось: " + otherTime / ONE_MINUTE + " : "
                + otherTime % ONE_MINUTE + " мин.");

            setTextStopWatch();
            setParamInfo(count);

            int countPauses = parameters[PARAMETER_PAUSE_ONE]
                    .getNumberExecutions() + parameters[PARAMETER_PAUSE_TWO]
                    .getNumberExecutions();
            mParamsInfo.setText(
                    "Вдохов: " + parameters[0].getNumberExecutions()
                            + " \n" + "Выдохов: "
                            + parameters[2].getNumberExecutions() + " \n"
                            + "Пауз: " + countPauses);
        }

        /** Sets the counter for the desired parameter. */
        private void setStopWatch() {
            if (stopwatch == 0) {
                count = count == COUNT_PARAMETERS - 1 ? 0 : count + 1;
                stopwatch = parameters[count].getOperatingTime();
                parameters[count - 1].setNumberExecutions(1);

                Vibrator vibrator = (Vibrator)
                        getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(MILLISECOND_VIBRATOR);
            }
        }

        /**
         * Setting the text of the View item.
         *
         * @see TextView mStopwatch
         */
        private void setTextStopWatch() {
            String duration, seconds;

            if (stopwatch > ONE_MINUTE) {
                seconds = stopwatch / ONE_MINUTE >= CLOCK_ELEMENT
                        ? String.valueOf(stopwatch)
                        : "0" + stopwatch % ONE_MINUTE;
                duration = stopwatch / ONE_MINUTE + ":" + seconds;
            } else {
                seconds = stopwatch >= CLOCK_ELEMENT
                        ? String.valueOf(stopwatch)
                        : "0" + stopwatch;
                duration = "00:" + seconds;
            }
            mStopwatch.setText(duration);
        }

        /** Setting constant values for parameters. */
        private void setParamsValue() {
            parameters[TrainingEngine.PARAMETER_INHALE] =
                    new Parameter(inhale);
            parameters[TrainingEngine.PARAMETER_PAUSE_ONE] =
                    new Parameter(pauseAfterInhale);
            parameters[TrainingEngine.PARAMETER_EXHALE] =
                    new Parameter(exhale);
            parameters[TrainingEngine.PARAMETER_PAUSE_TWO] =
                    new Parameter(pauseAfterExhale);
        }
    }

    /**
     * Setting information in the user interface about the active parameter.
     * @param idParameter - the ID of the active parameter.
     */
    private void setParamInfo(final int idParameter) {
        int id = idParameter - 1;
        switch (id) {
            case TrainingEngine.PARAMETER_PAUSE_ONE:
                setParamsInfo("вдох", "пауза", "выдох");
                break;
            case TrainingEngine.PARAMETER_EXHALE:
                setParamsInfo("пауза", "выдох", "пауза");
                break;
            case TrainingEngine.PARAMETER_PAUSE_TWO:
                setParamsInfo("выдох", "пауза", "вдох");
                break;
            case TrainingEngine.PARAMETER_INHALE:
                setParamsInfo("пауза", "вдох", "пауза");
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
}
