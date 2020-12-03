package Model;

public class EpisodeItem {
    Integer Id;
    String EpisodeName;
    String Episode_description;
    String ImageUrl;
    String fileUrl;

    public EpisodeItem(Integer id, String episodeName, String episode_description, String imageUrl, String fileUrl) {
        Id = id;
        EpisodeName = episodeName;
        Episode_description = episode_description;
        ImageUrl = imageUrl;
        this.fileUrl = fileUrl;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEpisodeName() {
        return EpisodeName;
    }

    public void setEpisodeName(String episodeName) {
        EpisodeName = episodeName;
    }

    public String getEpisode_description() {
        return Episode_description;
    }

    public void setEpisode_description(String episode_description) {
        Episode_description = episode_description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}