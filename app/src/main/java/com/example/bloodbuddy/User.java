package com.example.bloodbuddy;

import java.io.Serializable;

public class User implements Serializable {

    private String id; // Add id field
    private String name;
    private String email;
    private String phone;
    private String district;
    private String taluk;
    private String gender;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String id, String name, String email, String phone, String district, String taluk, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.district = district;
        this.taluk = taluk;
        this.gender = gender;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", district='" + district + '\'' +
                ", taluk='" + taluk + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
