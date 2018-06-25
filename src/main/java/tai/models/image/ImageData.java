package tai.models.image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ImageData implements Serializable {
    private String imageLink;
    private List<String> tags;


    public ImageData(String imageLink) {
        this.imageLink = imageLink;
        this.tags = new LinkedList<>();
    }
    @JsonCreator
    public ImageData(@JsonProperty("imageLink") String imageLink, @JsonProperty("tags") List<String> tags) {
        this.imageLink = imageLink;
        this.tags = tags;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
