package org.oodms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.oodms.dto.DataWrapper;
import org.oodms.entity.University;
import org.oodms.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "objectdb-database"
    );

    @PostMapping("/new-user")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        var em = emf.createEntityManager();
        var tx = em.getTransaction();
        Map<String, Object> response = new HashMap<>();


        try {
            if (user.getId() != 0) {
                response.put("message", "User already exists");
                response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (user.getUniversity().getId() != 0) {
                TypedQuery<University> query = em.createQuery(
                        "SELECT u FROM University u WHERE u.id = :id",
                        University.class
                );
                University university = query.setParameter(
                        "id", user.getUniversity().getId()
                ).getSingleResult();
                university.getUsers().add(user);
                user.setUniversity(university);
                em.persist(university);
            } else {
                em.persist(user.getUniversity());
            }

            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "We have an error");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User created");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable int id) {
        var em = emf.createEntityManager();
        var tx = em.getTransaction();

        tx.begin();
//        em.remove(em.find(User.class, id));
        Query query = em.createQuery("DELETE FROM User u WHERE u.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        tx.commit();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User deleted");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/preload-from-json")
    public ResponseEntity<Map<String, Object>> loadUsersFromJson() {
        String path = "users.json";
        ObjectMapper mapper = new ObjectMapper();
        var em = emf.createEntityManager();
        var tx = em.getTransaction();

        // TRUNCATE but ids don't set to 1
        tx.begin();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM University").executeUpdate();
        tx.commit();

        try {
            DataWrapper dataWrapper = mapper.readValue(new File(path), DataWrapper.class);
            Map<String, University> universities = new HashMap<String, University>();
            Map<String, List<User>> users = new HashMap<String, List<User>>();

            for (User user : dataWrapper.getUsers()) {
                String universityName = user.getUniversity().getName();
                universities.put(universityName, user.getUniversity());
                // insert into users map
                users.putIfAbsent(universityName, new ArrayList<>());
                users.get(universityName).add(user);
                universities.get(universityName).setUsers(users.get(universityName));
            }

            for (University university : universities.values()) {
                tx.begin();
                em.persist(university);
                tx.commit();
            }

            for (User user : dataWrapper.getUsers()) {
                tx.begin();
                user.setUniversity(universities.get(user.getUniversity().getName()));
                em.persist(user);
                tx.commit();
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User loaded");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
