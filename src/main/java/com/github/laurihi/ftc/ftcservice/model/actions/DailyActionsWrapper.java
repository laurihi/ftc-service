package com.github.laurihi.ftc.ftcservice.model.actions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyActionsWrapper {

    private String userHandle;
    private LocalDate date;
    private List<DailyAction> dailyActions = new ArrayList<>();

    public DailyActionsWrapper(){}
    public DailyActionsWrapper(String userHandle, LocalDate date, List<DailyAction> dailyActions){
        this.userHandle = userHandle;
        this.date = date;
        this.dailyActions.addAll(dailyActions);
    }

    public String getUserHandle() {
        return userHandle;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<DailyAction> getDailyActions() {
        return dailyActions;
    }
}
