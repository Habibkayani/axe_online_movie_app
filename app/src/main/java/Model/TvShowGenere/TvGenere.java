package Model.TvShowGenere;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvGenere {

    @SerializedName("Genre")
    @Expose
    private String genre;
    @SerializedName("Movies")
    @Expose
    private Movies movies;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Movies getMovies() {
        return movies;
    }

    public void setMovies(Movies movies) {
        this.movies = movies;
    }

}