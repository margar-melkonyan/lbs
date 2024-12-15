package org.oodms.entity.temp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table (name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int duration;
    private Timestamp releaseDate;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album Album;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "artist_song",
            joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "song_id", referencedColumnName = "id")
    )
    private List<Artist> Artists;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private Genre Genre;

    @Override
    public String toString() {
        return String.format("Title: %s, Duration: %d seconds, ReleaseDate: %s", title, duration, releaseDate.toString());
    }
}
