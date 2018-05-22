package tai.models.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Override
    Optional<Image> findById(Long aLong);

    Optional<Image> findByImageLink(String link);
}
