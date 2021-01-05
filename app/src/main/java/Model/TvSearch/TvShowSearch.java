package Model.TvSearch;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Model.Search.Body;

public class TvShowSearch {

    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("body")
    @Expose
    private List<Body1> body = null;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<Body1> getBody() {
        return body;
    }

    public void setBody(List<Body1> body) {
        this.body = body;
    }

}