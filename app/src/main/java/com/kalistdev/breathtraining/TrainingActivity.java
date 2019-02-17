package com.kalistdev.breathtraining;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class TrainingActivity extends AppCompatActivity {
     private final int ONE_MINUTE = 60;

    private TextView mTimeInfo,
     mStopwatch,
     mParamsInfo,
     mParam1,
     mParam2,
     mParam3;

     private Button buttonStop;

    /**
     * AsyncWork - переменная отвечает за работу TrainingEngine
     * boolStop - отвечает за двойное нажатие кнопки STOP,
     *      если false , то кнопка нажата 1 раз иначе 2 раза.
     */
    private boolean
            AsyncWork,
            boolStop;

    private int
            allTime,
            inhale,
            exhale,
            pause_a,
            pause_b,
            count,
            time,
            stopwatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        boolStop = false;

        TextView mNameTraining = findViewById(R.id.name);
        mTimeInfo = findViewById(R.id.timeInfo);
        mStopwatch = findViewById(R.id.stopwatch);
        mParamsInfo = findViewById(R.id.paramsInfo);
        buttonStop = findViewById(R.id.btmstop);

        mParam1 = findViewById(R.id.param1);
        mParam2 = findViewById(R.id.param2);
        mParam3 = findViewById(R.id.param3);

        allTime = getIntent().getIntExtra("AllTime", 0);
        inhale = getIntent().getIntExtra("Inhale", 0);
        exhale = getIntent().getIntExtra("Exhale", 0);
        pause_a = getIntent().getIntExtra("Pause_a", 0);
        pause_b = getIntent().getIntExtra("Pause_b", 0);

        mNameTraining.setText(getIntent().getStringExtra("Name"));

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!boolStop){
                    Toast toast = Toast.makeText(getApplicationContext(), "Нажмите еще для остановки.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.show();
                    boolStop = true;
                }else{
                    AsyncWork = false;
                }

            }
        });

        final TrainingEngine trainingEngine = new TrainingEngine();

        //Отдельный поток, отсчет до начала ( после 3 секунд запускает TrainingEngine ).
        Thread initialPause = new Thread(new Runnable() {
            int countSec = 3;
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    while (true)
                    {
                        mStopwatch.setText("НАЧАЛО ЧЕРЕЗ " + countSec);
                        TimeUnit.SECONDS.sleep(1);
                        countSec--;
                        if (countSec == 0){
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

    @SuppressLint("StaticFieldLeak")
    class TrainingEngine extends AsyncTask{

        private Parameter[] parameters = new Parameter[4];
        int otherTime;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            setParamsValue();
            AsyncWork = true;
            count = 0;
            stopwatch = parameters[count].getOperatingTime();
            time = time * ONE_MINUTE;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            while (AsyncWork){
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

                if (otherTime <= 0)
                    AsyncWork = false;
            }
            finish();
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

            mTimeInfo.setText(
                    "Время тренировки: " + allTime + " мин.\n" +
                    "Прошло: " + time / ONE_MINUTE + " : " + time % ONE_MINUTE + " мин.\n" +
                    "Осталось: " + otherTime / ONE_MINUTE + " : " + otherTime % ONE_MINUTE + " мин.");

            setTextStopWatch();
            setParamInfo(count);

            int countPauses = parameters[1].getNumberExecutions() + parameters[3].getNumberExecutions();
            mParamsInfo.setText(
                    "Вдохов: " + parameters[0].getNumberExecutions() + " \n" +
                    "Выдохов: " + parameters[2].getNumberExecutions() + " \n" +
                    "Пауз: " + countPauses);
        }

        private void setStopWatch(){
            if (stopwatch == 0) {
                count = count == 3 ? 0 : count + 1 ;
                stopwatch = parameters[count].getOperatingTime();
                setParamCount(count);
                Log.d("Test", stopwatch+ " - " + count);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(700);
            }
        }

        /**
         * Установки текста View элемента 
         * @see TextView mStopwatch
         */
        private void setTextStopWatch(){
            String time, seconds;
            
            if(stopwatch > ONE_MINUTE){
                seconds = stopwatch / ONE_MINUTE >= 10 ? String.valueOf(stopwatch) : "0" + stopwatch % ONE_MINUTE;
                time = stopwatch / ONE_MINUTE + ":" + seconds;
            }else {
                seconds = stopwatch >= 10 ? String.valueOf(stopwatch) : "0" + stopwatch;
                time = "00:" + seconds;
            }
            mStopwatch.setText(time);
        }

        private void setParamCount(int id){
            switch (id){
                case 1:{
                    parameters[0].setNumberExecutions(1);
                    break;
                }
                case 2:{
                    parameters[1].setNumberExecutions(1);
                    break;
                }
                case 3:{
                    parameters[2].setNumberExecutions(1);
                    break;
                }
                case 4:{
                    parameters[3].setNumberExecutions(1);
                    break;
                }
            }
        }

        private void setParamsValue(){
            parameters[0]= new Parameter(inhale);
            parameters[1]= new Parameter(pause_a);
            parameters[2]= new Parameter(exhale);
            parameters[3]= new Parameter(pause_b);
        }
    }

    private void setParamInfo(int id){
        switch (id) {
            case 1: {
                setParamsInfo("вдох", "пауза", "выдох");
                break;
            }
            case 2: {
                setParamsInfo("пауза", "выдох", "пауза");
                break;
            }
            case 3: {
                setParamsInfo("выдох", "пауза", "вдох");
                break;
            }
            case 4: {
                setParamsInfo("пауза", "вдох", "пауза");
                break;
            }
        }
    }

    private void setParamsInfo(String one_params, String two_params, String three_params){
        mParam1.setText(one_params);
        mParam2.setText(two_params);
        mParam3.setText(three_params);
    }
}
