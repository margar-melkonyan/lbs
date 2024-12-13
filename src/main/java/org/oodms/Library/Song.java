package org.oodms.Library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.oodms.Library.Meta.AdditionalInformation;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Title;
    private int Duration;
    private Timestamp ReleaseDate;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album Album;

    @ManyToMany(mappedBy = "songs", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Artist> Artists;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre Genre;

    @Embedded
    private AdditionalInformation AuditInformation;

    public Song() {}

    public Song(String Title, int Duration, Timestamp ReleaseDate) {
        this.Title = Title;
        this.Duration = Duration;
        this.ReleaseDate = ReleaseDate;
        this.AuditInformation = new AdditionalInformation();
    }

    // Getters and Setters
    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public Timestamp getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(Timestamp ReleaseDate) {
        this.ReleaseDate = ReleaseDate;
    }

    public List<Artist> getArtists() {
        return Artists;
    }

    public void setArtists(List<Artist> Artists) {
        this.Artists = Artists;
    }

    public Genre getGenre() {
        return Genre;
    }

    public void setGenre(Genre Genre) {
        this.Genre = Genre;
    }

    public void setAlbum(Album Album) {
        this.Album = Album;
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
        return String.format("Title: %s, Duration: %d seconds, ReleaseDate: %s", Title, Duration, ReleaseDate.toString());
    }
}
