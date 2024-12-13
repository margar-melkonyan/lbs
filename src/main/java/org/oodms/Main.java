package org.oodms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import org.oodms.Library.Album;
import org.oodms.Library.Artist;
import org.oodms.Library.Genre;
import org.oodms.Library.Song;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void persistData(EntityManager em, EntityManagerFactory emf) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File jsonFile = new File("albums.json");
            RootData rootData = objectMapper.readValue(jsonFile, RootData.class);

            List<Album> albums = rootData.getAlbums();
            List<Genre> genres = rootData.getGenres();
            List<Artist> artists = rootData.getArtists();

            // Persist genres first
            em.getTransaction().begin();
            for (Genre genre : genres) {
                em.persist(genre);
            }

            for (Album album : albums) {
                em.persist(album);
                for (Song song : album.getSongs()) {
                    song.setAlbum(album);
                    System.out.println(song.getArtists());
                    song.setGenre(findGenreByName(genres, song.getGenre().getTitle()));

                    em.persist(song);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }


    @Nullable
    private static Genre findGenreByName(List<Genre> genres, String genreName) {
        for (Genre genre : genres) {
            if (genre.getTitle().equalsIgnoreCase(genreName)) {
                return genre;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "objectdb-database"
        );
        EntityManager em = emf.createEntityManager();

        persistData(em, emf);
    }
}
