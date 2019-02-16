package com.mattcorwin.petitonwebapp.models;

import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=25, message="Usesrname must be between 3 and 25 characters")
    private String username;

    @NotNull
    @Size(min=6, max=61, message="Password must be between 6 and 35 characters")
    private String password;

    @NotNull
    @Size(min=6, max=61, message="")
    private String passwordRetype;

    @NotNull
    @Size(min=1, max=35)
    private String firstName;

    @NotNull
    @Size(min=1, max=35)
    private String lastName;

    @OneToMany
    @JoinColumn(name = "employee_id")
    private List<TurnIn> turnIns = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "employee_id")
    private List<Comment> comments = new ArrayList<>();

    private double accountBalance;

    private int active;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Employee() {}

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public List<TurnIn> getTurnIns() {
        return turnIns;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<TurnIn> getSelectedTurnIns() {
        //Todo:sort by date descending
        if (turnIns.size() < 11) {

            return this.turnIns;
        }
        List<TurnIn> selectedTurnIns = new ArrayList<>();
        for (int i = (turnIns.size() - 1); i > turnIns.size() - 10; i--) {

            selectedTurnIns.add(turnIns.get(i));
        }
        return selectedTurnIns;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void addToAccountBalance(double income) {
        this.accountBalance += income;
    }

    public void addTurnIn(TurnIn turnIn) {
        this.turnIns.add(turnIn);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public String getPasswordRetype() {
        return passwordRetype;
    }

    public void setPasswordRetype(String passwordRetype) {
        this.passwordRetype = passwordRetype;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
