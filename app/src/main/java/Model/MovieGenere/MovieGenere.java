package Model.MovieGenere;

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class MovieGenere {

    @SerializedName("Genre")
    @Expose
    private String genre;
    @SerializedName("Movies")
    @Expose
    private Movie3 movie3;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Movie3 getMovie3() {
        return movie3;
    }

    public void setMovie3(Movie3 movie3) {
        this.movie3 = movie3;
    }
}