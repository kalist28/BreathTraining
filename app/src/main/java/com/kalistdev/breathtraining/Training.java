package com.kalistdev.breathtraining;

class Training {
    private int _id;
    private String _name;
    private int _inhale;
    private int _exhale;
    private int _pause_a;
    private int _pause_b;
    private int _time;

    Training(int _id, String _name, int _inhale, int _exhale, int _pause_a, int _pause_b, int _time) {
        this._id = _id;
        this._name = _name;
        this._inhale = _inhale;
        this._exhale = _exhale;
        this._pause_a = _pause_a;
        this._pause_b = _pause_b;
        this._time = _time;
    }

    int get_id() {
        return _id;
    }

    String get_name() {
        return _name;
    }

    int get_inhale() {
        return _inhale;
    }

    int get_exhale() {
        return _exhale;
    }

    int get_pause_a() {
        return _pause_a;
    }

    int get_pause_b() {
        return _pause_b;
    }

    int get_time() {
        return _time;
    }
}
