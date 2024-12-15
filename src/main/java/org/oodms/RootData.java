package org.oodms;

import lombok.Getter;
import lombok.Setter;
import org.oodms.entity.temp.Album;
import org.oodms.entity.temp.Artist;
import org.oodms.entity.temp.Genre;

import java.util.List;

@Setter
@Getter
public class RootData {
    private List<Album> albums;
    private List<Genre> genres;
    private List<Artist> artists;
}
