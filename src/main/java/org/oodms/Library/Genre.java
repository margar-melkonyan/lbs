package org.oodms.Library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.oodms.Library.Meta.AdditionalInformation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Title;

    @OneToMany (mappedBy = "song", cascade = CascadeType.ALL)
    private List<Song> Songs;

    @Embedded
    private AdditionalInformation AuditInformation;

    public Genre() {}
    public Genre(String title) {
        Title = title;
        AuditInformation = new AdditionalInformation();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<Song> getSongs() {
        return Songs;
    }

    public void setSongs(List<Song> songs) {
        this.Songs = songs;
    }

    @PrePersist
    public void setCreatedAt() {
        if (this.AuditInformation == null) {
            this.AuditInformation = new AdditionalInformation();
        }

        this.AuditInformation.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.AuditInformation.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.AuditInformation.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return  String.format("Title: %s", Title);
    }
}
