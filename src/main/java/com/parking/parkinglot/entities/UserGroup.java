package com.parking.parkinglot.entities;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usergroups")
public class UserGroup {
    @Column(name = "user_group")
    private String userGroup;

    @Column(name = "username")
    private String username;

    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}