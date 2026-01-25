package com.parking.parkinglot.ejb;

import jakarta.ejb.EJBException;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.entities.UserGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Stateless
public class UsersBean {
    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @Inject
    PasswordBean passwordBean;
    @PersistenceContext
    EntityManager entityManager;

    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        List<String> usernames = new ArrayList<>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }

        Map<String, List<String>> groupsByUsername = new HashMap<>();
        if (!usernames.isEmpty()) {
            List<UserGroup> userGroups = entityManager
                    .createQuery("SELECT ug FROM UserGroup ug WHERE ug.username IN :usernames", UserGroup.class)
                    .setParameter("usernames", usernames)
                    .getResultList();
            for (UserGroup userGroup : userGroups) {
                groupsByUsername
                        .computeIfAbsent(userGroup.getUsername(), key -> new ArrayList<>())
                        .add(userGroup.getUserGroup());
            }
        }

        for (User user : users) {
            List<String> groups = groupsByUsername.getOrDefault(user.getUsername(), new ArrayList<>());
            UserDto dto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), groups);
            userDtos.add(dto);
        }
        return userDtos;
    }

    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }

    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }

    public Collection<String> findUsernamesByUserIds(Collection<Long> userIds) {
    List<String> usernames =
            entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds", String.class).setParameter("userIds", userIds).getResultList();
        return usernames;
    }

    public List<UserDto> findUsersByUserIds(Collection<Long> userIds) {
        List<User> users =
                entityManager.createQuery("SELECT u FROM User u WHERE u.id IN :userIds", User.class)
                        .setParameter("userIds", userIds)
                        .getResultList();
        return copyUsersToDto(users);
    }
}
