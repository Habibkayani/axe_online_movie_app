package Model.AllTvshows;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import Model.AllMovies.Movie2;

public class PostTVShows {


    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("movie")
    @Expose
    private List<Movie> movieList = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
