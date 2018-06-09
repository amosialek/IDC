package tai.models.image_tag;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tai.models.image.Image;
import tai.models.tags.Tag;
import tai.models.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ImageTag implements Serializable {
    @EmbeddedId
    private ImageTagPK id = new ImageTagPK();

    @JsonIgnore
    @ManyToOne
    @Cascade(value = CascadeType.ALL)
    @MapsId("imageId")
    @JoinColumn(name = "IMAGE_ID")
    private Image image;

    @ManyToOne
    @Cascade(value = CascadeType.ALL)
    @MapsId("tagId")
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @ManyToMany(mappedBy = "imageTags")
    private Set<User> userSet = new HashSet<>();

    @Column(name = "COUNT")
    private Integer count;

    public ImageTag() {
    }

    public ImageTag(Image image, Tag tag) {
        this.image = image;
        this.tag = tag;
        this.count = 1;
    }

    public ImageTagPK getId() {
        return id;
    }

    public void setId(ImageTagPK id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
}
