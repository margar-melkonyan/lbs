package org.oodms.entity.temp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "album")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private int releaseYear;

    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER)
    private List<Song> songs;

    @Override
    public String toString() {
        return  String.format("Title: %s, Release Year: %d", title, releaseYear);
    }
}
