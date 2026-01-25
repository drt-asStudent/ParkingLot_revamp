package com.parking.parkinglot.ejb;

import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.entities.UserGroup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserGroupMembershipTest {
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUpEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
    }

    @AfterAll
    static void tearDownEntityManagerFactory() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    void usersInCarAndUserGroupsAreAdminAndDictator() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            persistUser(entityManager, "admin", "admin@example.com", "hashed");
            persistUser(entityManager, "dictator", "dictator@example.com", "hashed");
            persistUser(entityManager, "guest", "guest@example.com", "hashed");

            persistGroup(entityManager, "admin", "READ_CARS");
            persistGroup(entityManager, "admin", "WRITE_CARS");
            persistGroup(entityManager, "admin", "READ_USERS");
            persistGroup(entityManager, "admin", "WRITE_USERS");

            persistGroup(entityManager, "dictator", "READ_CARS");
            persistGroup(entityManager, "dictator", "WRITE_CARS");
            persistGroup(entityManager, "dictator", "READ_USERS");
            persistGroup(entityManager, "dictator", "WRITE_USERS");
            /*
            persistGroup(entityManager, "gonzo", "READ_CARS");
            persistGroup(entityManager, "gonzo", "WRITE_CARS");
            persistGroup(entityManager, "gonzo", "READ_USERS");
            persistGroup(entityManager, "gonzo", "WRITE_USERS");
            */
            persistGroup(entityManager, "guest", "READ_STATS");

            entityManager.getTransaction().commit();

            List<String> usernames = entityManager
                    .createQuery(
                            "SELECT DISTINCT ug.username FROM UserGroup ug WHERE ug.userGroup IN :groups ORDER BY ug.username",
                            String.class)
                    .setParameter("groups", List.of("READ_CARS", "WRITE_CARS", "READ_USERS", "WRITE_USERS"))
                    .getResultList();

            assertEquals(List.of("admin", "dictator"), usernames);
        } finally {
            entityManager.close();
        }
    }

    private static void persistUser(EntityManager entityManager, String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        entityManager.persist(user);
    }

    private static void persistGroup(EntityManager entityManager, String username, String group) {
        UserGroup userGroup = new UserGroup();
        userGroup.setUsername(username);
        userGroup.setUserGroup(group);
        entityManager.persist(userGroup);
    }
}
