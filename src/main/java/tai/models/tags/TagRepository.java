package tai.models.tags;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Override
    Optional<Tag> findById(Long aLong);

    Optional<Tag> findByTagName(String name);
}
