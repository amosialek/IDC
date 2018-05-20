package tai.models.image_tag;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import tai.models.image.Image;
import tai.models.tags.Tag;

import javax.persistence.*;

public class ImageTag {
    @EmbeddedId
    private ImageTagPK id;

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

    @Column(name = "COUNT")
    private Integer count;

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
}
