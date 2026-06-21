package com.fashionhub.model;

import java.sql.Timestamp;

public class User {


private int userId;
private String fullName;
private String email;
private String phone;
private String password;
private String address;
private String city;
private String state;
private String pincode;
private Timestamp createdAt;

public User() {
}

public User(int userId, String fullName, String email, String phone, String password,
            String address, String city, String state, String pincode,
            Timestamp createdAt) {
    this.userId = userId;
    this.fullName = fullName;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.address = address;
    this.city = city;
    this.state = state;
    this.pincode = pincode;
    this.createdAt = createdAt;
}

public int getUserId() {
    return userId;
}

public void setUserId(int userId) {
    this.userId = userId;
}

public String getFullName() {
    return fullName;
}

public void setFullName(String fullName) {
    this.fullName = fullName;
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

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

public String getCity() {
    return city;
}

public void setCity(String city) {
    this.city = city;
}

public String getState() {
    return state;
}

public void setState(String state) {
    this.state = state;
}

public String getPincode() {
    return pincode;
}

public void setPincode(String pincode) {
    this.pincode = pincode;
}

public Timestamp getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
}

@Override
public String toString() {
    return "User [userId=" + userId +
            ", fullName=" + fullName +
            ", email=" + email +
            ", phone=" + phone +
            ", address=" + address +
            ", city=" + city +
            ", state=" + state +
            ", pincode=" + pincode +
            ", createdAt=" + createdAt + "]";
}


}
