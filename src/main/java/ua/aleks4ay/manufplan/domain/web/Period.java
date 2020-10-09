package ua.aleks4ay.manufplan.domain.web;

public class Period {
    private String beginDay;
    private String endDay;
    private boolean epicenter;

    public Period() {
    }

    public Period(String beginDay, String endDay, boolean epicenter) {
        this.beginDay = beginDay;
        this.endDay = endDay;
        this.epicenter = epicenter;
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

    public boolean isEpicenter() {
        return epicenter;
    }

    public void setEpicenter(boolean epicenter) {
        this.epicenter = epicenter;
    }
}
