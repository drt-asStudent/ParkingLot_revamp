package com.parking.parkinglot.common;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link com.parking.parkinglot.entities.User}
 */
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private List<String> userGroups;

    public UserDto(Long id, String username, String email, List<String> userGroups) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userGroups = userGroups;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getUserGroups() {
        return userGroups;
    }
}
