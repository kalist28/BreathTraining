package com.kalistdev.breathtraining;

/**
 * Breath Training Application
 *
 * This file is part of the Breath Training package.
 * This class parameter, describes the number of its execution,
 * the time of each and the overall performance.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
class Parameter {

    /** Operating time of parameter. */
    private int mOperatingTime;

    /** Number of repetitions. */
    private int mNumberExecutions;

    /** Operating full time. */
    private int mTotalTime;

    /**
     * Constructor - full initialization.
     *
     * @param operatingTime - operating time of parameter.
     */
    Parameter(final int operatingTime) {
        this.mOperatingTime    = operatingTime;
        this.mNumberExecutions = 0;
        this.mTotalTime        = 0;
    }

    /**
     * Function to get value of field {@link Parameter#mOperatingTime}.
     * @return operating time of parameter.
     */
    public int getOperatingTime() {
        return mOperatingTime;
    }

    /**
     * Function to get value of field {@link Parameter#mNumberExecutions}.
     * @return number of repetitions.
     */
    public int getNumberExecutions() {
        return mNumberExecutions;
    }

    /**
     * Function to get value of field {@link Parameter#mTotalTime}.
     * @return operating full time.
     */
    public int getTotalTime() {
        return mTotalTime;
    }

    /**
     * Function to set value of field.
     *
     * Function considers the number of performances and total time.
     *
     * @param numberExecution - number of accomplishment for summation.
     */
    public void setNumberExecutions(final int numberExecution) {
        this.mNumberExecutions += numberExecution;
        mTotalTime += mOperatingTime;
    }

}
