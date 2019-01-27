package com.mattcorwin.petitonwebapp.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnIn {

    private int id;

    private LocalDate date;

    private int statedCount;

    private int actualCount;

    private double validity;

    private double perSignaturePayout;

    private double totalPayout;

    private int employeeId;

    public TurnIn() {}

    public TurnIn(LocalDate date, int statedCount, int actualCount, double validity, double perSignaturePayout, double totalPayout, int employeeId) {
        this.date = date;
        this.statedCount = statedCount;
        this.actualCount = actualCount;
        this.validity = validity;
        this.perSignaturePayout = perSignaturePayout;
        this.totalPayout = totalPayout;
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStatedCount() {
        return statedCount;
    }

    public void setStatedCount(int statedCount) {
        this.statedCount = statedCount;
    }

    public int getActualCount() {
        return actualCount;
    }

    public void setActualCount(int actualCount) {
        this.actualCount = actualCount;
    }

    public double getValidity() {
        return validity;
    }

    public void setValidity(double validity) {
        this.validity = validity;
    }

    public double getPerSignaturePayout() {
        return perSignaturePayout;
    }

    public void setPerSignaturePayout(double perSignaturePayout) {
        this.perSignaturePayout = perSignaturePayout;
    }

    public double getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(double totalPayout) {
        this.totalPayout = totalPayout;
    }

    public int getEmployeeId() {
        return employeeId;
    }



}
