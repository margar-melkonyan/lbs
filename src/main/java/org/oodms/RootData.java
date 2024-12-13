package org.oodms;

import org.oodms.Library.Album;
import org.oodms.Library.Artist;
import org.oodms.Library.Genre;

import java.util.List;

public class RootData {
    private List<Album> albums;
    private List<Genre> genres;
    private List<Artist> artists;

    // Getters and setters
    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
