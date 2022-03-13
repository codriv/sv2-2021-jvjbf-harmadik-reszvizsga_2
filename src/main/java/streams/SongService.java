package streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SongService {

    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
    }

    public Optional<Song> shortestSong() {
        return songs.stream().min(Comparator.comparing(Song::getLength));
    }

    public List<Song> findSongByTitle (String title) {
        return songs.stream().filter(song -> title.equals(song.getTitle())).toList();
    }

    public boolean isPerformerInSong(Song song, String performer) {
        return song.getPerformers().stream().filter(p -> performer.equals(p)).toList().size() > 0;
    }

    public List<String> titlesBeforeDate (LocalDate date) {
        return songs.stream().filter(song -> date.isAfter(song.getRelease())).map(Song::getTitle).toList();
    }
}
