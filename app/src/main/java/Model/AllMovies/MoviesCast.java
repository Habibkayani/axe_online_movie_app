package Model.AllMovies;
import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class MoviesCast {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("role")
    @Expose
    private Object role;
    @SerializedName("image")
    @Expose
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
