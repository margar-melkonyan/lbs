package org.oodms.Library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.oodms.Library.Meta.AdditionalInformation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int Id;
    private String Title;
    private int ReleaseYear;

    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Song> Songs;

    @Embedded
    private AdditionalInformation AuditInformation;

    public Album() {}
    public Album(String title, int releaseYear) {
        Title = title;
        ReleaseYear = releaseYear;
        AuditInformation = new AdditionalInformation();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getReleaseYear() {
        return ReleaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        ReleaseYear = releaseYear;
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
        return  String.format("Title: %s, Release Year: %d", Title, ReleaseYear);
    }
}
