package Model;

import java.io.Serializable;
import java.util.List;

public class TvShowAllCategory implements Serializable {
    Integer CatagoryId;
    String CatagoryTitle;
    private List<TvShowCategoryItem> TvShowCategoryItemList = null;

    public TvShowAllCategory(Integer catagoryId, String catagoryTitle, List<TvShowCategoryItem> tvShowCategoryItemList) {
        CatagoryId = catagoryId;
        CatagoryTitle = catagoryTitle;
        TvShowCategoryItemList = tvShowCategoryItemList;
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

    public List<TvShowCategoryItem> getTvShowCategoryItemList() {
        return TvShowCategoryItemList;
    }

    public void setTvShowCategoryItemList(List<TvShowCategoryItem> tvShowCategoryItemList) {
        TvShowCategoryItemList = tvShowCategoryItemList;
    }
}