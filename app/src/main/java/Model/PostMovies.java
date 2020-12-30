package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostMovies {


    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("movie")
    @Expose
    private List<Movie2> movie2 = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Movie2> getMovie2() {
        return movie2;
    }

    public void setMovie2(List<Movie2> movie2) {
        this.movie2 = movie2;
    }
}
