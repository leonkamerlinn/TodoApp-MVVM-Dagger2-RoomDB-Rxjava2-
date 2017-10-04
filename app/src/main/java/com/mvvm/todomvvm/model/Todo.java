package com.mvvm.todomvvm.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "email",
        childColumns = "user_email"
))
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    private String description;
    @ColumnInfo(name = "user_email")
    private String userEmail;

    @Embedded
    private User user;


    @Ignore
    public Todo(String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public Todo(String description, boolean isCompleted, User user) {
        this.isCompleted = isCompleted;
        this.description = description;
        this.user = user;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return String.format("%s %b", description, isCompleted);
    }


}
