package com.example.foodapp.Model;

public class User {

    private String Phone;
    private String Password;
    private String Name;

    public User(String name, String password) {

        Password = password;
        Name = name;
    }

    public User(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
