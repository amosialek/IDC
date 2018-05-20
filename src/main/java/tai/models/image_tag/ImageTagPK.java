package tai.models.image_tag;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ImageTagPK {
    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "TAG_ID")
    private Long tagId;
}
