package Model;

public class SimilarMovie {
    String MovieName;
    String ImageUrl;

    public SimilarMovie(String movieName, String imageUrl) {
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
