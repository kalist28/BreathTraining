package com.kalistdev.breathtraining;

class Parameter {
    private int operatingTime;
    private int numberExecutions;
    private int totalTime;

    public Parameter(int operatingTime) {
        this.operatingTime = operatingTime;
        this.numberExecutions = 0;
        this.totalTime = 0;
    }

    public int getOperatingTime() {
        return operatingTime;
    }

    public int getNumberExecutions() {
        return numberExecutions;
    }

    public void setNumberExecutions(int numberExecutions) {
        this.numberExecutions += numberExecutions;
        totalTime += operatingTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

}
