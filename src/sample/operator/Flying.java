package sample.operator;

import java.time.LocalDate;

public class Flying extends Operator {
    private LocalDate date;
    private String who;
    private String timeOfDay;
    private int contr;
    private int hour;
    private int minute;

    public Flying(LocalDate date, String who, String timeOfDay, int contr, int hour, int minute) {
        this.date = date;
        this.who = who;
        this.timeOfDay = timeOfDay;
        this.contr = contr;
        this.hour = hour;
        this.minute = minute;
    }

    public Flying(String date, String who, String timeOfDay, int contr, int hour, int minute) {
        this.date = LocalDate.parse(date);
        this.who = who;
        this.timeOfDay = timeOfDay;
        this.contr = contr;
        this.hour = hour;
        this.minute = minute;
    }

    public String getDate() {
        return String.valueOf(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getContr() {
        return contr;
    }

    public void setContr(int contr) {
        this.contr = contr;
    }
}
