package tai.models.image;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tai.models.image_tag.ImageTag;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;

    public ImageService(ImageRepository imageRepository, TagRepository tagRepository) {
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    public Optional<Image> findByLink(String link) {
        return imageRepository.findByImageLink(link);
    }

    public Optional<Tag> findByTag(String tag) {
        return tagRepository.findByTagName(tag);
    }

    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    public Image add(ImageData data) throws EntityExistsException {
        if (findByLink(data.getImageLink()).isPresent()) throw new EntityExistsException();
        Image image = new Image();
        Set<ImageTag> tags = new HashSet<>();
        data.getTags().forEach(tag -> {
            if (findByTag(tag).isPresent()) {
                tags.add(new ImageTag(image, findByTag(tag).get()));
            } else {
                tags.add(new ImageTag(image, new Tag(tag)));
            }
        });
        image.setImageLink(data.getImageLink());
        image.setImageTags(tags);
        return imageRepository.save(image);
    }

    public Image update(Long id, ImageData data) throws EntityNotFoundException {
        Image image = findById(id).orElseThrow(EntityNotFoundException::new);
        Set<ImageTag> tags = new HashSet<>();
        data.getTags().forEach(tag -> {
            if (findByTag(tag).isPresent()) {
                //tag istnieje w bazie
                boolean flag = true;
                for (ImageTag imageTag : image.getImageTags()) {
                    if (imageTag.getTag().getTagName().equals(tag)) {
                        imageTag.setCount(imageTag.getCount() + 1);
                        flag = false;
                        break;
                    }
                }
                //tag istnieje w bazie, ale nie jest powiazany z obrazkiem
                if(flag){
                    tags.add(new ImageTag(image, findByTag(tag).get()));
                }
            } else {
                //tag nie istnieje w bazie, zatem nie jest tez powiazany z obrazkiem
                tags.add(new ImageTag(image, new Tag(tag)));
            }
        });
        image.setImageLink(data.getImageLink());
        image.setImageTags(tags);
        return imageRepository.save(image);
    }

    public void delete(Long id) throws EntityNotFoundException {
        if (imageRepository.findById(id).isPresent()) {
            imageRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Image get(Long id) throws EntityNotFoundException {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
