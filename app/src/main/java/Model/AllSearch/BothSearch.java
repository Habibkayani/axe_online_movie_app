package Model.AllSearch;

import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class BothSearch {

    @SerializedName("head")
    @Expose
    private String head;
    @SerializedName("body")
    @Expose
    private List<Body3> body3 = null;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<Body3> getBody3() {
        return body3;
    }

    public void setBody3(List<Body3> body3) {
        this.body3 = body3;
    }
}