package com.kalistdev.breathtraining;

/**
 * Класс описывающий обьект квадратной тренировки дыхания.
 *
 * @autor Dmitriy Kalistratov
 * @mail kalistratov.d.m@gmail.com
 * @version 1.0
 */
class Training {

    /** Уникальный номер тренировки. */
    private int _id;

    /** Название тренировки.*/
    private String _name;

    /** Время для вдоха.*/
    private int _inhale;

    /** Время для выдоха.*/
    private int _exhale;

    /** Время паузы после вдоха.*/
    private int _pause_a;

    /** Время паузы после выдоха.*/
    private int _pause_b;

    /** Время полной тренировки.*/
    private int _time;

    /**
     * Конструктор - создание нового объекта с определенными значениями.
     * Полная инициализация обьекта.
     * @param _id - уникальный номер тренировки.
     * @param _name - название тренировки.
     * @param _inhale - время для вдоха.
     * @param _exhale - время для выдоха.
     * @param _pause_a - время паузы после вдоха.
     * @param _pause_b - время паузы после выдоха.
     * @param _time - время полной тренировки.
     */
    Training(int _id, String _name, int _inhale, int _exhale, int _pause_a, int _pause_b, int _time) {
        this._id = _id;
        this._name = _name;
        this._inhale = _inhale;
        this._exhale = _exhale;
        this._pause_a = _pause_a;
        this._pause_b = _pause_b;
        this._time = _time;
    }

    /**
     * Функция получения уникального номера тренировки {@link Training#_id}
     * @return возвращает уникальный номер тренировки.
     */
    int get_id() {
        return _id;
    }

    /**
     * Функция получения названия тренировки {@link Training#_name}
     * @return возвращает название тренировки.
     */
    String get_name() {
        return _name;
    }

    /**
     * Функция получения времени на вдох {@link Training#_inhale}
     * @return возвращает время для вдоха.
     */
    int get_inhale() {
        return _inhale;
    }

    /**
     * Функция получения времени на выдох {@link Training#_exhale}
     * @return возвращает время для выдоха.
     */
    int get_exhale() {
        return _exhale;
    }

    /**
     * Функция получения времени на паузу после вдоха {@link Training#_pause_a}
     * @return возвращает время паузы после вдоха.
     */
    int get_pause_a() {
        return _pause_a;
    }

    /**
     * Функция получения времени на паузу после выдоха {@link Training#_pause_b}
     * @return возвращает время паузы после выдоха.
     */
    int get_pause_b() {
        return _pause_b;
    }

    /**
     * Функция получения времени полной тренировки {@link Training#_time}
     * @return возвращает время полной тренировки.
     */
    int get_time() {
        return _time;
    }
}
