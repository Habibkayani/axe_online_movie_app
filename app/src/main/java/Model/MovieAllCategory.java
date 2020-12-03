package Model;

import java.io.Serializable;
import java.util.List;

public class MovieAllCategory implements Serializable {
    Integer CatagoryId;
    String CatagoryTitle;
    private List<MovieCategoryItem> movieCategoryItemList = null;

    public MovieAllCategory(Integer catagoryId, String catagoryTitle, List<MovieCategoryItem> movieCategoryItemList) {
        CatagoryId = catagoryId;
        CatagoryTitle = catagoryTitle;
        this.movieCategoryItemList = movieCategoryItemList;
    }

    public Integer getCatagoryId() {
        return CatagoryId;
    }

    public void setCatagoryId(Integer catagoryId) {
        CatagoryId = catagoryId;
    }

    public String getCatagoryTitle() {
        return CatagoryTitle;
    }

    public void setCatagoryTitle(String catagoryTitle) {
        CatagoryTitle = catagoryTitle;
    }

    public List<MovieCategoryItem> getMovieCategoryItemList() {
        return movieCategoryItemList;
    }

    public void setMovieCategoryItemList(List<MovieCategoryItem> movieCategoryItemList) {
        this.movieCategoryItemList = movieCategoryItemList;
    }
}