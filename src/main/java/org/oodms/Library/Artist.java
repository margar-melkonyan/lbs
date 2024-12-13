package org.oodms.Library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.oodms.Library.Meta.AdditionalInformation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Name;
    private int DebutYear;
    private String Bio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "artist_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Song> songs;

    @Embedded
    private AdditionalInformation AuditInformation;

    public Artist() {}

    public Artist(String Name, int DebutYear, String Bio) {
        this.Name = Name;
        this.DebutYear = DebutYear;
        this.Bio = Bio;
        this.AuditInformation = new AdditionalInformation();
    }

    // Getters and Setters
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String Bio) {
        this.Bio = Bio;
    }

    public int getDebutYear() {
        return DebutYear;
    }

    public void setDebutYear(int DebutYear) {
        this.DebutYear = DebutYear;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    // PrePersist and PreUpdate for auditing
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
        return String.format("Name: %s, Debut Year: %d, Bio: %s", Name, DebutYear, Bio);
    }
}
