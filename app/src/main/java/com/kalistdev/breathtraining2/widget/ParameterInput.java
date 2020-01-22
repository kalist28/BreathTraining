package com.kalistdev.breathtraining2.widget;

import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.kalistdev.breathtraining2.R;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * Displays the name of the parameter and
 * receives characteristics from the user.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class ParameterInput extends RelativeLayout {

    /** Name parameter. */
    private TextView mName;

    /** Measurement parameter. */
    private TextView mMeasurement;

    /** Get users parameter. */
    private EditText mInput;

    /**
     * Constructor - initialize object.
     * @param context - app context.
     */
    public ParameterInput(final Context context) {
        super(context);
    }

    /**
     * Constructor - initialize object.
     * @param context   - app context.
     * @param attrs     - user arguments(parameters).
     */
    public ParameterInput(final Context context,
                          final AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater
                = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.widget_parameter_input, this);

        mName = findViewById(R.id.parameter_name);
        mMeasurement = findViewById(R.id.parameter_measurement);
        mInput = findViewById(R.id.parameter_input);
    }

    /** Initialize object.
     *
     * @param name          - mName training.
     * @param measurement   - mMeasurement or measurement.
     * @param defaultValue  - mInput or defaultValue.
     */
    public final void initialize(final String name,
                           final String measurement,
                           final String defaultValue) {
        this.mName.setText(name);
        this.mMeasurement.setText(measurement);
        this.mInput.setHint(defaultValue);
    }

    /**
     * Function to get value of field {@link ParameterInput#mInput}.
     * @return returns the edit text.
     */
    public final EditText getEditText() {
        return mInput;
    }

    /**
     * Function to get value of field {@link ParameterInput#mInput}.
     * @return returns the text.
     */
    public final String getText() {
        return mInput.getText().toString();
    }
}
