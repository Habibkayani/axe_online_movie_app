package Model;

public class SimilarTvShows {
    String MovieName;
    String ImageUrl;

    public SimilarTvShows(String movieName, String imageUrl) {
        MovieName = movieName;
        ImageUrl = imageUrl;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
