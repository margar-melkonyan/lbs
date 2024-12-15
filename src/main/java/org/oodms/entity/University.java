package org.oodms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oodms.entity.meta.AdditionalInformation;
import org.oodms.entity.meta.City;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int capacity;
    @OneToMany (mappedBy = "university")
    private List<User> users;
    @Embedded
    private City city;
    @Embedded
    private AdditionalInformation additionalInformation;

    @PrePersist
    public void prePersist() {
        if (additionalInformation == null) {
            additionalInformation = new AdditionalInformation();
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        additionalInformation.setCreatedAt(now);
        additionalInformation.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate() {
        if (additionalInformation == null) {
            additionalInformation = new AdditionalInformation();
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        additionalInformation.setUpdatedAt(now);
    }
}
