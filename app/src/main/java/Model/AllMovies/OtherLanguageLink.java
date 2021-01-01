package Model.AllMovies;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherLanguageLink {

    @SerializedName("language")
    @Expose
    private Object language;
    @SerializedName("link")
    @Expose
    private String link;

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}