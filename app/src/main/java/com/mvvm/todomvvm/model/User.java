package com.mvvm.todomvvm.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.mvvm.todomvvm.utils.SecureUtils;

import java.util.Date;

/**
 * Created by Leon on 17.9.2017..
 */

@Entity(indices = {
        @Index(
                value = {"email"},
                unique = true
        )
})
public class User {


    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "first_name")

    private String firstName;
    @ColumnInfo(name = "last_name")

    private String lastName;
    private String email;
    private String password;
    private String salt;
    private Date created;



    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.created = new Date();
    }

    @Ignore
    public User(String firstName, String lastName, String email, String password, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.created = new Date();
    }

    public int getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format("%s %n %s %n %s %n %s %n %s", getFirstName(), getLastName(), getEmail(), getUid(), getCreated());
    }
}
