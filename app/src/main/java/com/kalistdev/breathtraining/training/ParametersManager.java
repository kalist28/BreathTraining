package com.kalistdev.breathtraining.training;

/**
 * Breath TrainingRecord Application
 *
 * The class describes the breathing parameters ParametersManager.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class ParametersManager {
    /** The dimension of the array with parameters. */
    public static final int COUNT_PARAMETERS = 4;

    /** Position of the second inhale view_element_training in the array. */
    public static final int PARAMETER_INHALE = 0;

    /** Position of the thirst pause view_element_training in the array. */
    public static final int PARAMETER_PAUSE_AFTER_INHALE = 1;

    /** Position of the second exhale view_element_training in the array. */
    public static final int PARAMETER_EXHALE = 2;

    /** Position of the second pause view_element_training in the array. */
    public static final int PARAMETER_PAUSE_AFTER_EXHALE = 3;

    /** Operating full time training. */
    private int mTotalTimeTraining;

    /**
     * Array of parameters having their own values.
     * @see Parameter
     */
    private Parameter[] parameters = new Parameter[COUNT_PARAMETERS];

    /**
     * Constructor - create a new object.
     * @param inhale operating time of inhale.
     * @param exhale operating time of parameter exhale.
     * @param pauseAfterInhale operating time of parameter pauseAfterInhale.
     * @param pauseAfterExhale operating time of parameter pauseAfterExhale.
     */
    public ParametersManager(final int inhale,
                             final int exhale,
                             final int pauseAfterInhale,
                             final int pauseAfterExhale) {
            parameters[ParametersManager.PARAMETER_INHALE]
                    = new Parameter(inhale);
            parameters[ParametersManager.PARAMETER_PAUSE_AFTER_INHALE]
                    = new Parameter(pauseAfterInhale);
            parameters[ParametersManager.PARAMETER_EXHALE]
                    = new Parameter(exhale);
            parameters[ParametersManager.PARAMETER_PAUSE_AFTER_EXHALE]
                    = new Parameter(pauseAfterExhale);
    }

    /**
     * Function to get value of field {@link ParametersManager#parameters}.
     * @param id - parameter id.
     * @return returns the parameter.
     */
    public Parameter getParameterId(final int id) {
        if (id < parameters.length) {
            return parameters[id];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /** Function set total time training.
     *
     * @param seconds - total time seconds.
     */
    public void setTotalTime(final int seconds) {
        this.mTotalTimeTraining = seconds;
    }

    /** Function to get value of field.
     * @return returns the total time all parameters in milliseconds.
     */
    public int getTotalTime() {
        return mTotalTimeTraining;
    }

    /**
     * Breath TrainingRecord Application
     *
     * This file is part of the Breath TrainingRecord package.
     * This class parameter, describes the number of its execution,
     * the time of each and the overall performance.
     *
     * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     * @version 1.0
     */
    public class Parameter {

        /** Operating time of parameter. */
        private int mOperatingTime;

        /** Number of repetitions. */
        private int mNumberExecutions;

        /** Operating full time. */
        private int mTotalTime;

        /**
         * Constructor.
         */
        public Parameter() { }

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

}
