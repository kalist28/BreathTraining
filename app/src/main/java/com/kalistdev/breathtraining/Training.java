package com.kalistdev.breathtraining;

/**
 * Класс описывающий обьект квадратной тренировки дыхания.
 *
 * @author Dmitriy Kalistratov kalistratov.d.m@gmail.com
 * @version 1.0
 */
class Training {

    /** Уникальный номер тренировки. */
    private int id;

    /** Название тренировки.*/
    private String name;

    /** Время для вдоха.*/
    private int inhale;

    /** Время для выдоха.*/
    private int exhale;

    /** Время паузы после вдоха.*/
    private int pauseAfterInhale;

    /** Время паузы после выдоха.*/
    private int pauseAfterExhale;

    /** Время полной тренировки.*/
    private int time;

    /**
     * Конструктор - создание нового объекта с полной инициализацией.
     * @param id - уникальный номер тренировки.
     * @param name - название тренировки.
     * @param inhale - время для вдоха.
     * @param exhale - время для выдоха.
     * @param pauseAfterInhale - время паузы после вдоха.
     * @param pauseAfterExhale - время паузы после выдоха.
     * @param time - время полной тренировки.
     */
    Training(final int id, final String name, final int inhale, final int exhale,
             final int pauseAfterInhale, final int pauseAfterExhale, final int time) {
        this.id = id;
        this.name = name;
        this.inhale = inhale;
        this.exhale = exhale;
        this.pauseAfterInhale = pauseAfterInhale;
        this.pauseAfterExhale = pauseAfterExhale;
        this.time = time;
    }

    /**
     * Функция получения уникального номера тренировки {@link Training#id}.
     * @return возвращает уникальный номер тренировки.
     */
    int getId() {
        return id;
    }

    /**
     * Функция получения названия тренировки {@link Training#name}.
     * @return возвращает название тренировки.
     */
    String getName() {
        return name;
    }

    /**
     * Функция получения времени на вдох {@link Training#inhale}.
     * @return возвращает время для вдоха.
     */
    int getInhale() {
        return inhale;
    }

    /**
     * Функция получения времени на выдох {@link Training#exhale}.
     * @return возвращает время для выдоха.
     */
    int getExhale() {
        return exhale;
    }

    /**
     * Функция получения времени на паузу после вдоха {@link Training#pauseAfterInhale}.
     * @return возвращает время паузы после вдоха.
     */
    int getPauseAfterInhale() {
        return pauseAfterInhale;
    }

    /**
     * Функция получения времени на паузу после выдоха {@link Training#pauseAfterExhale}.
     * @return возвращает время паузы после выдоха.
     */
    int getPauseAfterExhale() {
        return pauseAfterExhale;
    }

    /**
     * Функция получения времени полной тренировки {@link Training#time}.
     * @return возвращает время полной тренировки.
     */
    int getTime() {
        return time;
    }
}
