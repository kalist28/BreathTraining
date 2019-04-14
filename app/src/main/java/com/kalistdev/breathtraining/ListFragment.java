package com.kalistdev.breathtraining;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class ListFragment extends Fragment {

    private static RecyclerView recyclerView;
    private static DataBaseHelper dataBaseHelper;
    private static List<Training> trainings = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBaseHelper = new DataBaseHelper(getContext());
        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        UpdateList(view);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.FloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateNewTrainingDialog createNewTrainingDialog = new CreateNewTrainingDialog(getContext());
                assert getFragmentManager() != null;
                createNewTrainingDialog.show(getFragmentManager(), null);
            }
        });
    }

    private static void UpdateList(View view){
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DataBaseHelper.DATABASE_TABLE, null);
        cursor.moveToFirst();
        trainings.clear();
        while (!cursor.isAfterLast()){
            trainings.add(new Training(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)));
            cursor.moveToNext();
            Log.d("Count ", trainings.size() + "");

        }
        cursor.close();

        RVAdapter rvAdapter = new RVAdapter(trainings, new EditTrainingDialog(view.getContext()));
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(rvAdapter);
    }

    private static int GetNumber(String string, int minValue){

        return string.matches("") ? minValue : Integer.parseInt(string);

    }

    @SuppressLint("ValidFragment")
    static class CreateNewTrainingDialog extends DialogFragment {

        private Context context;

        @SuppressLint("ValidFragment")
        private CreateNewTrainingDialog(Context context){
            this.context = context;
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(context));
            @SuppressLint("InflateParams")
            final View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_training, null);

            final EditText mNameEditText = view.findViewById(R.id.nameEditText);
            final EditText mInhaleEditText = view.findViewById(R.id.inhaleEditText);
            final EditText mExhaleEditText = view.findViewById(R.id.exhaleEditText);
            final EditText mPauseA_EditText = view.findViewById(R.id.pauseA_EditText);
            final EditText mPauseB_EditText = view.findViewById(R.id.pauseB_EditText);
            final EditText mTimeEditText = view.findViewById(R.id.timeEditText);

            int numberTraining = trainings.size() + 1;
            mNameEditText.setText("Тренировка #" + numberTraining);
            builder.setTitle("Новая тренировка:");
            builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

                    db.execSQL("INSERT INTO training_list(name,inhale,pause_a,exhale,pause_b,time) VALUES (" +
                            "'" + mNameEditText.getText().toString() + "'," +
                            GetNumber(mInhaleEditText.getText().toString(), 4) + "," +
                            GetNumber(mPauseA_EditText.getText().toString(), 6) + "," +
                            GetNumber(mExhaleEditText.getText().toString(), 4) + "," +
                            GetNumber(mPauseB_EditText.getText().toString(), 6) + "," +
                            GetNumber(mTimeEditText.getText().toString(), 3) + ")");
                    db.close();

                    UpdateList(view);
                }

            }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setView(view);


            return builder.create();
        }

    }

    @SuppressLint("ValidFragment")
    static class EditTrainingDialog extends DialogFragment {

        private Context context;
        private Training training;

        EditTrainingDialog(Context context) {
            this.context = context;
        }

        void setTraining(Training training){
            this.training = training;
        }

        @NonNull
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            @SuppressLint("InflateParams")
            final View view = LayoutInflater.from(context).inflate(R.layout.dialog_training, null);

            final EditText sNameEditText = view.findViewById(R.id.nameEditText);
            final EditText sInhaleEditText = view.findViewById(R.id.inhaleEditText);
            final EditText sExhaleEditText = view.findViewById(R.id.exhaleEditText);
            final EditText sPauseA_EditText = view.findViewById(R.id.pauseA_EditText);
            final EditText sPauseB_EditText = view.findViewById(R.id.pauseB_EditText);
            final EditText sTimeEditText = view.findViewById(R.id.timeEditText);

            sNameEditText.setText(training.getName());
            sInhaleEditText.setText(String.valueOf(training.getInhale()));
            sExhaleEditText.setText(String.valueOf(training.getExhale()));
            sPauseA_EditText .setText(String.valueOf(training.getPause_a()));
            sPauseB_EditText.setText(String.valueOf(training.getPause_b()));
            sTimeEditText.setText(String.valueOf(training.getTime()));

            builder.setView(view);
            builder.setTitle("Изменить тренировку:")
                    .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase sqLiteDatabase = new DataBaseHelper(getContext()).getReadableDatabase();
                            sqLiteDatabase.execSQL("UPDATE training_list SET " +
                                    DataBaseHelper.KEY_NAME_TRAINING + " = '" + String.valueOf(sNameEditText.getText().toString()) + "' , " +
                                    "inhale = " + Integer.parseInt(sInhaleEditText.getText().toString()) + ", " +
                                    "pause_a = " + Integer.parseInt(sExhaleEditText.getText().toString()) + ", " +
                                    "exhale = " + Integer.parseInt(sPauseA_EditText.getText().toString()) + ", " +
                                    "pause_b = " + Integer.parseInt(sPauseB_EditText.getText().toString()) + ", " +
                                    "time = " + Integer.parseInt(sTimeEditText.getText().toString()) + " WHERE _id = " + training.getId());
                            sqLiteDatabase.close();
                            UpdateList(view);
                        }
                    }).setNegativeButton("Отмена", null);

            return builder.create();
        }
    }
}
