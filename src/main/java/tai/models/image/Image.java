package tai.models.image;

import tai.models.image_tag.ImageTag;
import tai.models.tags.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "IMAGES")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(name = "IMAGE_LINK")
    @NotBlank
    private String imageLink;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<ImageTag> imageTags = new HashSet<>();

    public Image() {

    }

    public Image(String imageLink){
        setImageLink(imageLink);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Set<ImageTag> getImageTags() {
        return imageTags;
    }

    public void setImageTags(Set<ImageTag> imageTags) {
        this.imageTags = imageTags;
    }
}
