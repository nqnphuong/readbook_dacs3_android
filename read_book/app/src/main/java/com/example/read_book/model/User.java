package com.example.read_book.model;

public class User {
    private int id_user, role, userAge;
    private String userEmail, userPassword, userFirstname, userCity, userDayofbirth, userImage1, userImage2, userAndress,
            userPhone, userDescription;


    public User(int id_user, int role, int userAge, String userAndress, String userCity, String userDayofbirth, String userDescription, String userEmail, String userImage1, String userImage2, String userFirstname,  String userPassword, String userPhone) {
        this.id_user = id_user;
        this.role = role;
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFirstname = userFirstname;
        this.userCity = userCity;
        this.userDayofbirth = userDayofbirth;
        this.userImage1 = userImage1;
        this.userImage2 = userImage2;
        this.userAndress = userAndress;
        this.userPhone = userPhone;
        this.userDescription = userDescription;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserDayofbirth() {
        return userDayofbirth;
    }

    public void setUserDayofbirth(String userDayofbirth) {
        this.userDayofbirth = userDayofbirth;
    }

    public String getUserImage1() {
        return userImage1;
    }

    public void setUserImage1(String userImage1) {
        this.userImage1 = userImage1;
    }

    public String getUserImage2() {
        return userImage2;
    }

    public void setUserImage2(String userImage2) {
        this.userImage2 = userImage2;
    }

    public String getUserAndress() {
        return userAndress;
    }

    public void setUserAndress(String userAndress) {
        this.userAndress = userAndress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }


}
