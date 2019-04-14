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
    private int pause_a;

    /** Время паузы после выдоха.*/
    private int pause_b;

    /** Время полной тренировки.*/
    private int time;

    /**
     * Конструктор - создание нового объекта с полной инициализацией.
     * @param id - уникальный номер тренировки.
     * @param name - название тренировки.
     * @param inhale - время для вдоха.
     * @param exhale - время для выдоха.
     * @param pause_a - время паузы после вдоха.
     * @param pause_b - время паузы после выдоха.
     * @param time - время полной тренировки.
     */
    Training(int id, String name, int inhale, int exhale, int pause_a, int pause_b, int time) {
        this.id = id;
        this.name = name;
        this.inhale = inhale;
        this.exhale = exhale;
        this.pause_a = pause_a;
        this.pause_b = pause_b;
        this.time = time;
    }

    /**
     * Функция получения уникального номера тренировки {@link Training#id}
     * @return возвращает уникальный номер тренировки.
     */
    int getId() {
        return id;
    }

    /**
     * Функция получения названия тренировки {@link Training#name}
     * @return возвращает название тренировки.
     */
    String getName() {
        return name;
    }

    /**
     * Функция получения времени на вдох {@link Training#inhale}
     * @return возвращает время для вдоха.
     */
    int getInhale() {
        return inhale;
    }

    /**
     * Функция получения времени на выдох {@link Training#exhale}
     * @return возвращает время для выдоха.
     */
    int getExhale() {
        return exhale;
    }

    /**
     * Функция получения времени на паузу после вдоха {@link Training#pause_a}
     * @return возвращает время паузы после вдоха.
     */
    int getPause_a() {
        return pause_a;
    }

    /**
     * Функция получения времени на паузу после выдоха {@link Training#pause_b}
     * @return возвращает время паузы после выдоха.
     */
    int getPause_b() {
        return pause_b;
    }

    /**
     * Функция получения времени полной тренировки {@link Training#time}
     * @return возвращает время полной тренировки.
     */
    int getTime() {
        return time;
    }
}
