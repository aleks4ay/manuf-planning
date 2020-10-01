package ua.aleks4ay.manufplan.domain.web;

import java.time.LocalDateTime;

public class Period {
    private LocalDateTime beginDay;
    private LocalDateTime endDay;

    public Period() {
    }

    public Period(LocalDateTime beginDay, LocalDateTime endDay) {
        this.beginDay = beginDay;
        this.endDay = endDay;
    }

    public LocalDateTime getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(LocalDateTime beginDay) {
        this.beginDay = beginDay;
    }

    public LocalDateTime getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDateTime endDay) {
        this.endDay = endDay;
    }
}
