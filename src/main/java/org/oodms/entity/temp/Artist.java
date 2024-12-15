package org.oodms.entity.temp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int debutYear;
    private String bio;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "artist_song",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id")
    )
    private List<Song> songs;

    @Override
    public String toString() {
        return String.format("Name: %s, Debut Year: %d, Bio: %s", name, debutYear, bio);
    }
}
