package tai.models.image_tag;

import org.springframework.data.jpa.repository.JpaRepository;
import tai.models.image.Image;
import tai.models.tags.Tag;

import java.util.Optional;

public interface ImageTagRepository extends JpaRepository<ImageTag, Long> {
    Optional<ImageTag> findById(ImageTagPK id);

    Optional<ImageTag> findByImage (Image image);

    Optional<ImageTag> findByTag (Tag tag);
}
