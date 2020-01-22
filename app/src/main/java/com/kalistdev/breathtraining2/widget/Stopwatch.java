package com.kalistdev.breathtraining2.widget;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * The basic training class.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class Stopwatch {

    /** The movement of the stopwatch ago. */
    public static final boolean COUNTDOWN = false;

    /** Moving the stopwatch forward. */
    public static final boolean COUNT = true;

    /** Describes one hour in minutes. */
    private static final int ONE_MINUTE = 60;

    /** Used to display the correct time. */
    private static final int CLOCK_ELEMENT = 10;

    /** Number of seconds. */
    private int seconds;

    /** Moving the stopwatch. */
    private boolean typeCount;

    /**
     * Constructor - create a new object with full initialization.
     *
     * @param sec - moving the stopwatch..
     */
    public Stopwatch(final int sec) {
        this.typeCount  = true;
        setSeconds(sec);
    }

    /**
     * Constructor - create a new object with full initialization.
     *
     * @param type - moving the stopwatch..
     */
    public Stopwatch(final boolean type) {
        this.typeCount  = type;
    }

    /**
     * Function to get value of field {@link Stopwatch#seconds}.
     * @return returns seconds.
     */
    public int getSeconds() {
        return seconds;
    }

    /** Function to put value seconds. */
    public void goStep() {
        if (typeCount) {
            seconds++;
        } else {
            if (seconds >= 0) {
                seconds--;
            }
        }
    }

    /** Function to set value seconds.
     *
     * @param sec - seconds.
     */
    public void setSeconds(final int sec) {
        this.seconds = sec;
    }

    /**
     * Function to get value of field.
     * @return returns time by format mm:ss.
     */
    public String getTime() {
        String duration, min, sec;

        min         = getNormalTime(seconds / ONE_MINUTE);
        sec         = getNormalTime(seconds % ONE_MINUTE);
        duration    =  min + ":" + sec;

        return duration;
    }

    /**
     * Function to get value of field.
     * @param seconds - total time in seconds.
     * @return returns time by format mm:ss.
     */
    public static String getTime(final int seconds) {
        String duration, min, sec;

        min         = getNormalTime(seconds / ONE_MINUTE);
        sec         = getNormalTime(seconds % ONE_MINUTE);
        duration    =  min + ":" + sec;

        return duration;
    }

    /**
     * Function to get value of field.
     * @param sec - total time in seconds.
     * @return returns time with high-end zeros.
     */
    private static String getNormalTime(final int sec) {
        String duration;
        if (sec >= CLOCK_ELEMENT) {
            duration = String.valueOf(sec);
        } else {
            duration = "0" + sec;
        }
        return duration;
    }
}
