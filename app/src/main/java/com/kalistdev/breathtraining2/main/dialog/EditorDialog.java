package com.kalistdev.breathtraining2.main.dialog;

import android.view.View;
import android.os.Bundle;
import java.util.Objects;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.kalistdev.breathtraining2.R;
import android.content.DialogInterface;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.kalistdev.breathtraining2.Record.Training;
import com.kalistdev.breathtraining2.widget.ParameterInput;
import com.kalistdev.breathtraining2.main.ListTrainingFragment;

/**
 * This class to create an internal dialog.
 * Dialog is designed to create a new list item.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 */
public class EditorDialog extends DialogFragment {

    /** User token to start the create function. */
    public static final int CREATE  = 0;

    /** User token to start the edit function. */
    public static final int EDIT    = 1;

    /** ParameterInput object containing info about name training. */
    private EditText mName;

    /** ParameterInput object containing info about inhale. */
    private ParameterInput mInhale;

    /** ParameterInput object containing info about exhale. */
    private ParameterInput mExhale;

    /** ParameterInput object containing info about pause after inhale. */
    private ParameterInput mPauseInhale;

    /** ParameterInput object containing info about pause after exhale. */
    private ParameterInput mPauseExhale;

    /** ParameterInput object containing info about time of training. */
    private ParameterInput mTime;

    /** Object of training. */
    private Training mTraining;

    /** Type work dialog. */
    private int mType;

    /** Params for create new training. */
    private final int   parOne  = 4,
                        parTwo  = 6,
                        parTime = 3;

    /**
     * Constructor - create a new object with full initialization.
     *
     * @param type - type work dialog.
     */
    public EditorDialog(final int type) {
        mType = type;
    }

    /**
     * Creating a dialog.
     *
     * This dialog to create a new workout bu the user.
     *
     * @param savedInstanceState - standard parameter.
     * @return ready dialog.
     */
    @NonNull
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final AlertDialog.Builder builder =
                new AlertDialog.Builder(Objects.requireNonNull(getContext()));

        @SuppressLint("InflateParams")
        final View v = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_training, null);

        mName               = v.findViewById(R.id.dialog_name);
        mInhale             = v.findViewById(R.id.dialog_inhale);
        mExhale             = v.findViewById(R.id.dialog_exhale);
        mPauseInhale        = v.findViewById(R.id.dialog_pauseA);
        mPauseExhale        = v.findViewById(R.id.dialog_pauseB);
        mTime               = v.findViewById(R.id.dialog_time);

        mInhale.initialize(getString(R.string.Inhale),
                getString(R.string.second),
                String.valueOf(parOne));

        mExhale.initialize(getString(R.string.Exhale),
                getString(R.string.second),
                String.valueOf(parOne));

        mPauseInhale.initialize(getString(R.string.Pause_after_inhale),
                getString(R.string.second),
                String.valueOf(parTwo));

        mPauseExhale.initialize(getString(R.string.Pause_after_exhale),
                getString(R.string.second),
                String.valueOf(parTwo));

        mTime.initialize(getString(R.string.Training_time),
                getString(R.string.minutes),
                String.valueOf(parTime));

        switch (mType) {
            case CREATE: {
                createTraining(builder);
                break;
            }

            case EDIT: {
                editTraining(builder);
                break;
            }

            default: {
                break;
            }
        }

        builder.setView(v);
        return builder.create();
    }

    /**
     * Function to set value of field.
     *
     * @param training - the object training you want to change.
     */
    public void setTraining(final Training training) {
        this.mTraining = training;
    }

    /** Create new training.
     *
     * @param builder - AlertDialog builder dialog.
     */
    private void createTraining(final AlertDialog.Builder builder) {
        int numberTraining = Training.listAll(Training.class).size();
        String newName
                = getString(R.string.training_number)
                + numberTraining;
        mName.setText(newName);
        builder.setTitle(getString(R.string.new_training));
        builder.setPositiveButton(getString(R.string.save),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        saveTraining();
                    }

                })
                .setNegativeButton(getString(R.string.cancellation), null);
    }

    /** Edit an existing training.
     *
     * @param builder - AlertDialog builder dialog.
     */
    private void editTraining(final AlertDialog.Builder builder) {
        mName.setText(mTraining.getName());
        mInhale.getEditText()
                .setText(String.valueOf(mTraining.getInhale()));
        mExhale.getEditText()
                .setText(String.valueOf(mTraining.getExhale()));
        mPauseInhale.getEditText()
                .setText(String.valueOf(mTraining.getPauseAfterInhale()));
        mPauseExhale.getEditText()
                .setText(String.valueOf(mTraining.getPauseAfterExhale()));
        mTime.getEditText()
                .setText(String.valueOf(mTraining.getTime()));

        builder.setTitle(getString(R.string.edit_training))
            .setPositiveButton(getString(R.string.save),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,
                                        final int which) {
                        saveTraining();
                    }
                })
            .setNegativeButton(getString(R.string.cancellation), null);
    }

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

    /** Save in database new training. */
    private void saveTraining() {
        Training training;

        if (mType == EDIT) {
            training = Training.findById(Training.class, mTraining.getId());
        } else {
            training = new Training();
        }
        training.initialize(
                mName.getText().toString(),
                getNumber(mInhale.getText(),       parOne),
                getNumber(mPauseInhale.getText(),  parTwo),
                getNumber(mExhale.getText(),       parOne),
                getNumber(mPauseExhale.getText(),  parTwo),
                getNumber(mTime.getText(),         parTime));
        training.save();
        ListTrainingFragment.updateList();
    }
}

