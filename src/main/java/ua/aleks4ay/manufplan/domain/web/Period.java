package ua.aleks4ay.manufplan.domain.web;

public class Period {
    private String beginDay;
    private String endDay;

    public Period() {
    }

    public Period(String beginDay, String endDay) {
        this.beginDay = beginDay;
        this.endDay = endDay;
    }

    public String getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(String beginDay) {
        this.beginDay = beginDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}
