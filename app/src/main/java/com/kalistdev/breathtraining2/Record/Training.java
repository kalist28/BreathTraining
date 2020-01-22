package com.kalistdev.breathtraining2.Record;

import com.orm.SugarRecord;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * The class describes object square technique breathing exercises.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Training extends SugarRecord {

    /** Название тренировки.*/
    private String mName;

    /** Время для вдоха.*/
    private int mInhale;

    /** Время для выдоха.*/
    private int mExhale;

    /** Время паузы после вдоха.*/
    private int mPauseAfterInhale;

    /** Время паузы после выдоха.*/
    private int mPauseAfterExhale;

    /** Время полной тренировки.*/
    private int mTime;

    /** Constructor - create a new object with full initialization. */
    public Training() { }

    /**
     * Constructor - create a new object with full initialization.
     *
     * @param name - название тренировки.
     * @param inhale - время для вдоха.
     * @param exhale - время для выдоха.
     * @param pauseAfterInhale - время паузы после вдоха.
     * @param pauseAfterExhale - время паузы после выдоха.
     * @param time - время полной тренировки.
     */
    public Training(
                final String name,
                final int inhale,
                final int exhale,
                final int pauseAfterInhale,
                final int pauseAfterExhale,
                final int time) {
        initialize(name,
                   inhale,
                   exhale,
                   pauseAfterInhale,
                   pauseAfterExhale,
                   time);
    }

    /** Initialize object.
     *
     * @param name - название тренировки.
     * @param inhale - время для вдоха.
     * @param exhale - время для выдоха.
     * @param pauseAfterInhale - время паузы после вдоха.
     * @param pauseAfterExhale - время паузы после выдоха.
     * @param time - время полной тренировки.
     */
    public final void initialize(
                    final String name,
                    final int inhale,
                    final int exhale,
                    final int pauseAfterInhale,
                    final int pauseAfterExhale,
                    final int time) {
        this.mName              = name;
        this.mInhale            = inhale;
        this.mExhale            = exhale;
        this.mPauseAfterInhale  = pauseAfterInhale;
        this.mPauseAfterExhale  = pauseAfterExhale;
        this.mTime              = time;
    }

    /**
     * Function to get value of field {@link Training#mName}.
     * @return returns the name of the workout.
     */
    public String getName() {
        return mName;
    }

    /**
     * Function to get value of field {@link Training#mInhale}.
     * @return returns the time to take a breath.
     */
    public int getInhale() {
        return mInhale;
    }

    /**
     * Function to get value of field {@link Training#mExhale}.
     * @return returns the time to exhale.
     */
    public int getExhale() {
        return mExhale;
    }

    /**
     * Function to get value of field {@link Training#mPauseAfterInhale}.
     * @return returns the time of the pause after a breath.
     */
    public int getPauseAfterInhale() {
        return mPauseAfterInhale;
    }

    /**
     * Function to get value of field {@link Training#mPauseAfterExhale}.
     * @return returns the time of the pause after the exhale.
     */
    public int getPauseAfterExhale() {
        return mPauseAfterExhale;
    }

    /**
     * Function to get value of field {@link Training#mTime}.
     * @return returns the time of full training.
     */
    public int getTime() {
        return mTime;
    }
}
