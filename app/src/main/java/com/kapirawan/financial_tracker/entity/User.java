package com.kapirawan.financial_tracker.entity;

public class User {
    private long _id;
    private String username;
    private String firstname;
    private String lastname;
    private String token;

    public User(long _id, String username, String firstname, String lastname, String token){
        this._id = _id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.token = token;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}