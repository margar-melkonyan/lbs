package org.oodms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oodms.entity.meta.AdditionalInformation;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private enum Gender {MALE, FEMALE};
    private enum Curriculum {BACHELOR, MASTER, PHD};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Curriculum curriculum;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    @ManyToOne (cascade = CascadeType.PERSIST)
    private University university;
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
