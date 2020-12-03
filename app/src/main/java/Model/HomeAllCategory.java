package Model;

import java.io.Serializable;
import java.util.List;

public class HomeAllCategory implements Serializable {
    Integer CatagoryId;
    String CatagoryTitle;
    private List<HomeCategoryItem> homeCategoryItemList = null;

    public HomeAllCategory(Integer catagoryId, String catagoryTitle, List<HomeCategoryItem> homeCategoryItemList) {
        this.CatagoryId = catagoryId;
        this.CatagoryTitle = catagoryTitle;
        this.homeCategoryItemList = homeCategoryItemList;
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

    public List<HomeCategoryItem> getHomeCategoryItemList() {
        return homeCategoryItemList;
    }

    public void setHomeCategoryItemList(List<HomeCategoryItem> homeCategoryItemList) {
        this.homeCategoryItemList = homeCategoryItemList;
    }
}