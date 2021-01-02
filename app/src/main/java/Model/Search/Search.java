package Model.Search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search{

    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("body")
    @Expose
    private List<Body> body = null;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }

}