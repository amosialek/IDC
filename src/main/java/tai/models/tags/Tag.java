package tai.models.tags;

import tai.models.image.Image;
import tai.models.image_tag.ImageTag;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TAGS")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "TAG_NAME")
    @NotBlank
    private String tagName;

    @OneToMany(mappedBy = "image")
    private Set<ImageTag> tagImageTags = new HashSet<>();

    public Tag() {
    }

    public Tag(@NotBlank String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<ImageTag> getImageTags() {
        return tagImageTags;
    }

    public void setImageTags(Set<ImageTag> tagImageTags) {
        this.tagImageTags = tagImageTags;
    }
}
