package com.honorfly.schoolsys.entry;

import com.honorfly.schoolsys.utils.dao.EntityObj;

import javax.persistence.*;

@Entity
@Table(name = "sys_user")
public class User extends EntityObj {

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String userName;


    @Column(name = "password")
    private String password;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}