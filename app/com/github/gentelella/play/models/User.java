package com.github.gentelella.play.models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.EnumValue;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fre on 15/07/16.
 */
@Entity
public class User extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public User(String name, String surname, String email, String password , UserType type) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String surname;

    @Column(unique = true)
    public String email;

    public String password;

    @Enumerated(EnumType.STRING)
    public User.UserType type;

    @Column(name = "created_at")
    @CreatedTimestamp
    public Date createdAt;

    public static enum UserType{
        @EnumValue("USER")
        USER,
        @EnumValue("ADMIN")
        ADMIN
    }

    public static Finder<Long, User> find = new Finder<Long,User>(User.class);

}

