package com.kalistdev.breathtraining2.Record;

import com.orm.SugarRecord;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * The class describes an extensible Sugar ORM object,
 * that stores the database of completed workouts.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Journal extends SugarRecord {

    /** Name of training. */
    private String mNameTraining;

    /** Containing json object containing information about the parameters. */
    private String mParamsInfo;

    /** User opinion about training. */
    private String mComment;

    /** Time of training. */
    private String mTime;

    /** Count positive stars. */
    private int mCountStars;

    /** Constructor - create object. */
    public Journal() { }

    /**
     * Function initialize this object.
     *
     * @param nameTraining  - name of training.
     * @param paramsInfo    - json object containing info about the parameters.
     * @param opinion       - user opinion.
     * @param time          - time of training.
     * @param countStars    - count positive stars.
     */
    public void initialize(final String nameTraining,
                           final String paramsInfo,
                           final String opinion,
                           final String time,
                           final int countStars) {
        this.mNameTraining  = nameTraining;
        this.mParamsInfo    = paramsInfo;
        this.mComment       = opinion;
        this.mTime          = time;
        this.mCountStars    = countStars;
    }

    /**
     * Function to get value of field {@link Journal#mNameTraining}.
     * @return returns the name of Training.
     */
    public String getNameTraining() {
        return mNameTraining;
    }

    /**
     * Function to get value of field {@link Journal#mParamsInfo}.
     * @return returns the json object containing info about the parameters.
     */
    public String getParamsInfo() {
        return mParamsInfo;
    }

    /**
     * Function to get value of field {@link Journal#mComment}.
     * @return returns user opinion.
     */
    public String getComment() {
        return mComment;
    }

    /**
     * Function to get value of field {@link Journal#mTime}.
     * @return returns time of training.
     */
    public String getTime() {
        return mTime;
    }

    /**
     * Function to get value of field {@link Journal#mCountStars}.
     * @return returns count positive stars.
     */
    public int getCountStars() {
        return mCountStars;
    }
}
