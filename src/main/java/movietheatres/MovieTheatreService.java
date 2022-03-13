package movietheatres;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path) {
        try (Scanner scanner = new Scanner(path)){
            while (scanner.hasNext()) {
                putShows(scanner.nextLine());
            }
            sortMovies();
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file!");
        }
    }

    private void putShows(String line) {
        String[] parts = line.split("-");
        String[] movieParts = parts[1].split(";");
        String cinema = parts[0];
        String title = movieParts[0];
        LocalTime startTime = LocalTime.parse(movieParts[1]);
        if (!shows.containsKey(cinema)) {
            shows.put(cinema, new ArrayList<Movie>());
        }
        shows.get(cinema).add(new Movie(title, startTime));
    }

    private void sortMovies() {
        for (Map.Entry<String, List<Movie>> entry: shows.entrySet()) {
            entry.getValue().sort(Comparator.comparing(Movie::getStartTime));
        }
    }

    public List<String> findMovie(String title) {
        return shows.keySet().stream().filter(cinema -> shows.get(cinema).stream().map(Movie::getTitle).toList().contains(title)).toList();
    }

    public LocalTime findLatestShow(String title) {
        return shows.values().stream().flatMap(m -> m.stream()).filter(movie -> title.equals(movie.getTitle())).max(Comparator.comparing(Movie::getStartTime)).orElseThrow(() -> new IllegalArgumentException("Movie not found!")).getStartTime();
    }
}
