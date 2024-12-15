package org.oodms.controller;

import lombok.RequiredArgsConstructor;
import org.oodms.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
@RequiredArgsConstructor
public class UserController {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "objectdb-database"
    );

    @PostMapping
    public void createUser(@RequestBody User user) {
        var em = emf.createEntityManager();
        var tx = em.getTransaction();

        tx.begin();
        try {
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }

//    public void deleteUser(@RequestBody User user) {
//        var em = emf.createEntityManager();
//        var tx = em.getTransaction();
//
//        tx.begin();
//        try {
//            em.persist(user);
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        }
//    }
}
