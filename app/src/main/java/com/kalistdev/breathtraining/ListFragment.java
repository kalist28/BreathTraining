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

     static void UpdateList(View view){
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


    @SuppressLint("ValidFragment")
    static class CreateNewTrainingDialog extends DialogFragment {

        private Context context;

        @SuppressLint("ValidFragment")
        public CreateNewTrainingDialog(Context context){
            this.context = context;
        }

        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(context));
            @SuppressLint("InflateParams")
            final View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_training, null);
            final EditText name = v.findViewById(R.id.name);
            final EditText inhale = v.findViewById(R.id.inhale);
            final EditText exhale = v.findViewById(R.id.exhale);
            final EditText pauseA = v.findViewById(R.id.pauseA);
            final EditText pauseB = v.findViewById(R.id.pauseB);
            final EditText time = v.findViewById(R.id.time);

            builder.setTitle("Новая тренировка:");
            builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

                    db.execSQL("INSERT INTO training_list(name,inhale,pause_a,exhale,pause_b,time) VALUES (" +
                            "'" + name.getText().toString() + "'," +
                            Integer.parseInt(inhale.getText().toString()) + "," +
                            Integer.parseInt(pauseA.getText().toString()) + "," +
                            Integer.parseInt(exhale.getText().toString()) + "," +
                            Integer.parseInt(pauseB.getText().toString()) + "," +
                            Integer.parseInt(time.getText().toString()) + ")");
                    db.close();

                    UpdateList(v);
                }
            }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setView(v);


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

        public void setTraining(Training training){
            this.training = training;
        }

        @NonNull
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            final View v = LayoutInflater.from(context).inflate(R.layout.dialog_edit_training, null);
            final EditText name = v.findViewById(R.id.name);
            final EditText inhale = v.findViewById(R.id.inhale);
            final EditText exhale = v.findViewById(R.id.exhale);
            final EditText pauseA = v.findViewById(R.id.pauseA);
            final EditText pauseB = v.findViewById(R.id.pauseB);
            final EditText time = v.findViewById(R.id.time);

            name.setText(training.get_name());
            inhale.setText(String.valueOf(training.get_inhale()));
            exhale.setText(String.valueOf(training.get_exhale()));
            pauseA .setText(String.valueOf(training.get_pause_a()));
            pauseB.setText(String.valueOf(training.get_pause_b()));
            time.setText(String.valueOf(training.get_time()));

            builder.setView(v);
            builder.setTitle("Изменить тренировку:")
                    .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase sqLiteDatabase = new DataBaseHelper(getContext()).getReadableDatabase();
                            sqLiteDatabase.execSQL("UPDATE training_list SET " +
                                    DataBaseHelper.KEY_NAME_TRAINING + " = '" + String.valueOf(name.getText().toString()) + "' , " +
                                    "inhale = " + Integer.parseInt(inhale.getText().toString()) + ", " +
                                    "pause_a = " + Integer.parseInt(pauseA.getText().toString()) + ", " +
                                    "exhale = " + Integer.parseInt(exhale.getText().toString()) + ", " +
                                    "pause_b = " + Integer.parseInt(pauseB.getText().toString()) + ", " +
                                    "time = " + Integer.parseInt(time.getText().toString()) + " WHERE _id = " + training.get_id());
                            sqLiteDatabase.close();
                            UpdateList(v);
                        }
                    }).setNegativeButton("Отмена", null);

            return builder.create();
        }
    }
}
