package com.mattcorwin.petitonwebapp.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TurnIn {

    @Id
    @GeneratedValue
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private int statedCount;

    private int actualCount;

    private double validity;

    private double perSignaturePayout;

    private double totalPayout;

    @ManyToOne
    private Employee employee;

    public TurnIn() {}

    public TurnIn(LocalDate date, int statedCount, int actualCount, double validity, double perSignaturePayout, double totalPayout, Employee employee) {
        this.date = date;
        this.statedCount = statedCount;
        this.actualCount = actualCount;
        this.validity = validity;
        this.perSignaturePayout = perSignaturePayout;
        this.totalPayout = totalPayout;
        this.employee = employee;
    }

    public int getId() {
        return id;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
