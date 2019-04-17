package com.kalistdev.breathtraining;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Breath Training Application
 *
 * This file is part of the Breath Training package.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class ListFragment extends Fragment {

    /** XML container view elements. */
    private static RecyclerView recyclerView;

    /**
     * DataBaseHelper object.
     * @see DataBaseHelper
     */
    private static DataBaseHelper dataBaseHelper;

    /** Array of training from DataBase. */
    private static List<Training> trainings = new ArrayList<>();

    @Override
    public final View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public final void onViewCreated(final View view,
                                    final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(getContext());
        recyclerView = view.findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        updateList(view);

        FloatingActionButton floatingActionButton =
                view.findViewById(R.id.FloatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                CreateNewTrainingDialog createNewTrainingDialog =
                        new CreateNewTrainingDialog(getContext());
                assert getFragmentManager() != null;
                createNewTrainingDialog.show(getFragmentManager(), null);
            }
        });
    }

    /**
     * Updating the list of training.
     * @param view getContext.
     */
    private static void updateList(final View view) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "
                + DataBaseField.DATABASE_TABLE.getValue(), null);

        cursor.moveToFirst();
        trainings.clear();
        while (!cursor.isAfterLast()) {
            trainings.add(new Training(
                    cursor.getInt(DataBaseField.KEY_ID.getId()),
                    cursor.getString(DataBaseField.KEY_NAME_TRAINING.getId()),
                    cursor.getInt(DataBaseField.KEY_INHALE.getId()),
                    cursor.getInt(DataBaseField.KEY_EXHALE.getId()),
                    cursor.getInt(DataBaseField.KEY_PAUSE_AFTER_INHALE.getId()),
                    cursor.getInt(DataBaseField.KEY_PAUSE_AFTER_EXHALE.getId()),
                    cursor.getInt(DataBaseField.KEY_TIME.getId())));
            cursor.moveToNext();
            Log.d("Count ", trainings.size() + "");
        }
        cursor.close();

        RVAdapter rvAdapter = new RVAdapter(trainings,
                new EditTrainingDialog(view.getContext()));
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(rvAdapter);
    }

    /**
     * This class to create an internal dialog.
     * Dialog is designed to create a new list item.
     *
     * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     */
    @SuppressLint("ValidFragment")
    static final class CreateNewTrainingDialog extends DialogFragment {

        /**
         * Function to get value of field.
         *
         * @param string - string with values
         * @param minValue - minimal value
         * @return The method gets the input value entered by the user,
         *         if the string is not empty, it returns the converted
         *         value otherwise it returns the minimum value.
         */
        private int getNumber(final String string, final int minValue) {
            return string.matches("") ? minValue : Integer.parseInt(string);
        }

        /** Interior context. */
        private Context mContext;

        /**
         * Constructor - create a new object with full initialization.
         *
         * @param context application context.
         */
        @SuppressLint("ValidFragment")
        private CreateNewTrainingDialog(final Context context) {
            this.mContext = context;
        }

        /**
         * Creating a dialog.
         *
         * This dialog to create a new workout bu the user.
         *
         * @param savedInstanceState - standard parameter.
         * @return ready dialog.
         */
        @SuppressLint("SetTextI18n")
        public Dialog onCreateDialog(final Bundle savedInstanceState) {

            final AlertDialog.Builder builder =
                    new AlertDialog.Builder(Objects.requireNonNull(mContext));
            @SuppressLint("InflateParams")
            final View view =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.dialog_training, null);

            final EditText mNameEditText =
                    view.findViewById(R.id.nameEditText);
            final EditText mInhaleEditText =
                    view.findViewById(R.id.inhaleEditText);
            final EditText mExhaleEditText =
                    view.findViewById(R.id.exhaleEditText);
            final EditText mPauseAfterInhaleEditText =
                    view.findViewById(R.id.pauseA_EditText);
            final EditText mPauseAfterExhaleEditText =
                    view.findViewById(R.id.pauseB_EditText);
            final EditText mTimeEditText =
                    view.findViewById(R.id.timeEditText);

            int numberTraining = trainings.size() + 1;
            mNameEditText.setText("Тренировка #" + numberTraining);
            builder.setTitle("Новая тренировка:");
            builder.setPositiveButton("Сохранить",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog,
                                    final int which) {
                    final int parOne = 4;
                    final int parTwo = 6;
                    final int parTime = 3;

                    SQLiteDatabase db =
                            new DataBaseHelper(mContext).getReadableDatabase();

                    db.execSQL(
                            "INSERT INTO training_list(name, inhale, "
                            + "mPauseAfterInhale,"
                            + " exhale, mPauseAfterExhale,time)"
                            + " VALUES (" + "'" + mNameEditText.getText()
                                    .toString() + "',"
                            + getNumber(mInhaleEditText.getText()
                                    .toString(), parOne) + ","
                            + getNumber(mPauseAfterInhaleEditText.getText()
                                    .toString(), parTwo) + ","
                            + getNumber(mExhaleEditText.getText()
                                    .toString(), parOne) + ","
                            + getNumber(mPauseAfterExhaleEditText.getText()
                                    .toString(), parTwo) + ","
                            + getNumber(mTimeEditText.getText()
                                    .toString(), parTime) + ")");
                    db.close();

                    updateList(view);
                }

            }).setNegativeButton("Отмена",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog,
                                    final int which) {
                    dialog.dismiss();
                }
            });
            builder.setView(view);

            return builder.create();
        }

    }

    /**
     * This class to edit an internal dialog.
     * Dialog is designed to edit a new list item.
     *
     * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     */
    @SuppressLint("ValidFragment")
    static class EditTrainingDialog extends DialogFragment {

        /** Interior context. */
        private Context mContext;

        /** Object of training. */
        private Training mTraining;

        /**
         * Constructor - full initialization.
         *
         * @param context application context.
         */
        EditTrainingDialog(final Context context) {
            this.mContext = context;
        }

        /**
         * Function to set value of field.
         *
         * @param training - element of training to edit its parameters.
         */
        void setTraining(final Training training) {
            this.mTraining = training;
        }

        /**
         * Creating a dialog.
         *
         * This dialog to edit a workout bu the user.
         *
         * @param savedInstanceState - standard parameter.
         * @return ready dialog.
         */
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            final android.app.AlertDialog.Builder builder =
                    new android.app.AlertDialog.Builder(mContext);
            @SuppressLint("InflateParams")
            final View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.dialog_training, null);

            final EditText sNameEditText =
                    view.findViewById(R.id.nameEditText);
            final EditText sInhaleEditText =
                    view.findViewById(R.id.inhaleEditText);
            final EditText sExhaleEditText =
                    view.findViewById(R.id.exhaleEditText);
            final EditText sPauseAfterInhaleEditText =
                    view.findViewById(R.id.pauseA_EditText);
            final EditText sPauseAfterExhaleEditText =
                    view.findViewById(R.id.pauseB_EditText);
            final EditText sTimeEditText =
                    view.findViewById(R.id.timeEditText);

            sNameEditText.setText(mTraining.getName());
            sInhaleEditText
                    .setText(String.valueOf(mTraining.getInhale()));
            sExhaleEditText
                    .setText(String.valueOf(mTraining.getExhale()));
            sPauseAfterInhaleEditText
                    .setText(String.valueOf(mTraining.getPauseAfterInhale()));
            sPauseAfterExhaleEditText
                    .setText(String.valueOf(mTraining.getPauseAfterExhale()));
            sTimeEditText
                    .setText(String.valueOf(mTraining.getTime()));

            builder.setView(view);
            builder.setTitle("Изменить тренировку:")
                    .setPositiveButton("Сохранить",
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog,
                                            final int which) {
                            SQLiteDatabase sqLiteDatabase =
                                    new DataBaseHelper(getContext())
                                            .getReadableDatabase();

                            sqLiteDatabase.execSQL("UPDATE training_list SET "
                                    + DataBaseField.KEY_NAME_TRAINING.getValue()
                                    + " = '" + String
                                    .valueOf(sNameEditText
                                            .getText().toString())
                                    + "' , " + "inhale = " + Integer
                                    .parseInt(sInhaleEditText
                                            .getText().toString())
                                    + ", " + "pause_a = " + Integer
                                    .parseInt(sExhaleEditText
                                            .getText().toString())
                                    + ", " + "mPauseAfterInhale " + Integer
                                    .parseInt(sPauseAfterInhaleEditText
                                            .getText().toString())
                                    + ", " + "mPauseAfterExhale = " + Integer
                                    .parseInt(sPauseAfterExhaleEditText
                                            .getText().toString())
                                    + ", " + "time = " + Integer
                                    .parseInt(sTimeEditText.getText()
                                            .toString())
                                    + " WHERE _id = " + mTraining.getId());
                            sqLiteDatabase.close();
                            updateList(view);
                        }
                    }).setNegativeButton("Отмена", null);

            return builder.create();
        }
    }
}
