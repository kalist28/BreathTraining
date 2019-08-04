package com.kalistdev.breathtraining.main.dialog;

import java.util.List;
import java.util.Objects;
import android.view.View;
import android.os.Bundle;
import android.app.Dialog;
import java.util.ArrayList;
import com.google.gson.Gson;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import com.kalistdev.breathtraining.R;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.kalistdev.breathtraining.Record.Journal;
import com.kalistdev.breathtraining.widget.Stopwatch;
import com.kalistdev.breathtraining.widget.StarsManager;
import com.kalistdev.breathtraining.widget.ParameterInfo;
import com.kalistdev.breathtraining.training.ParametersManager;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class JournalDialog extends DialogFragment {

    /** Containing info records. */
    private Journal mJournal;

    /** UI objects training parameters. */
    private List<ParameterInfo> params = new ArrayList<>();

    /**
     * Constructor - initialize object.
     *
     * @param journal - Journal object.
     */
    public JournalDialog(final Journal journal) {
        this.mJournal = journal;
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(Objects.requireNonNull(getContext()));

        @SuppressLint("InflateParams")
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_journal_record, null);

        ParametersManager pm
                = new Gson()
                        .fromJson(mJournal.getParamsInfo(),
                                  ParametersManager.class);

        setNameTraining(v);
        setParametersInfo(v, pm);
        setNumberExecution(v, pm);
        setComment(v);
        setStarsManager(v);

        builder.setView(v);
        builder.setPositiveButton("OK", null);
        return builder.create();
    }

    /** Fill array list and find objects.
     *
     * @param v - layout view object.
     */
    private void fillParams(final View v) {
        params.add((ParameterInfo) v.findViewById(R.id.dialog_inhale));
        params.add((ParameterInfo) v.findViewById(R.id.dialog_pauseA));
        params.add((ParameterInfo) v.findViewById(R.id.dialog_exhale));
        params.add((ParameterInfo) v.findViewById(R.id.dialog_pauseB));
    }

    /** Set info in UI about name training.
     *
     * @param v - layout view.
     */
    private void setNameTraining(final View v) {
        TextView nameTraining
                = v.findViewById(R.id.dialog_journal_name_training);
        nameTraining.setText(mJournal.getNameTraining());
    }

    /** Set info in UI about params training.
     *
     * @param v     - layout view object.
     * @param pm    - ParametersManager.
     */
    private void setParametersInfo(final View v,
                                   final ParametersManager pm) {
        TextView paramsInfo
                = v.findViewById(R.id.dialog_journal_params);
        StringBuilder paramsInfoBuilder = new StringBuilder();

        for (int i = 0; i < ParametersManager.COUNT_PARAMETERS; i++) {
            paramsInfoBuilder
                    .append(pm.getParameterId(i).getOperatingTime())
                    .append(", ");
        }
        paramsInfoBuilder
                .append(pm.getTotalTime())
                .append(".");
        paramsInfo.setText(paramsInfoBuilder.toString());
    }

    /** Set info in UI about number executions.
     *
     * @param v     - layout view object.
     * @param pm    - ParametersManager.
     */
    private void setNumberExecution(final View v,
                                    final ParametersManager pm) {
        ParameterInfo pInfo
                = v.findViewById(R.id.dialog_time);

        fillParams(v);
        String[] paramsName
                = {v.getResources().getString(R.string.Inhale),
                   v.getResources().getString(R.string.Pause_after_inhale),
                   v.getResources().getString(R.string.Exhale),
                   v.getResources().getString(R.string.Pause_after_exhale)};

        for (int i = 0; i < params.size(); i++) {
            params.get(i).initialize(paramsName[i],
                    String.valueOf(pm.getParameterId(i)
                                            .getNumberExecutions()));
        }

        pInfo.initialize(v.getResources().getString(R.string.Training_time),
                String.valueOf(new Stopwatch(pm.getTotalTime()).getTime()));
    }

    /** Set info in UI user comment.
     * @param v - layout view object.
     */
    private void setComment(final View v) {
        TextView comment
                = v.findViewById(R.id.dialog_journal_comment);
        comment.setText(mJournal.getComment());
    }

    /** Set setting stars manager.
     * @param v - layout view object.
     */
    private void setStarsManager(final View v) {
        StarsManager starsManager
                = v.findViewById(R.id.dialog_journal_stars);
        starsManager.setStars(mJournal.getCountStars());
        starsManager.hideAllText();
        starsManager.setClickable(false);
    }
}
