package com.kalistdev.breathtraining.widget;

import android.widget.TextView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.kalistdev.breathtraining.R;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * Displays the mName of the parameter and its characteristics parallel to it.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class ParameterInfo extends RelativeLayout {

    /** Name parameter. */
    private TextView mName;

    /** Characteristic parameter. */
    private TextView mInfo;

    /**
     * Constructor - initialize object.
     * @param context - app context.
     */
    public ParameterInfo(final Context context) {
        super(context);
    }

    /**
     * Constructor - initialize object.
     *
     * @param context   - app context.
     * @param attrs     - user arguments(parameters).
     */
    public ParameterInfo(final Context context,
                          final AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater
                = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.widget_parameter, this);

        mName = findViewById(R.id.parameter_name);
        mInfo = findViewById(R.id.parameter_info);
    }

    /** Initialize object.
     *
     * @param name - mName training.
     * @param info - mInfo or characteristic.
     */
    public void initialize(final String name,
                           final String info) {
        this.mName.setText(name);
        this.mInfo.setText(info);
    }

}
