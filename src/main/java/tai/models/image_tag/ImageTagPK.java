package tai.models.image_tag;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ImageTagPK implements Serializable {
    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "TAG_ID")
    private Long tagId;
}
