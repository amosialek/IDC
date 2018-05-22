package tai.models.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tai.models.exceptions.ExistsException;
import tai.models.exceptions.NotFoundException;
import tai.models.image_tag.ImageTag;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;

import java.util.*;

@Service
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;

    @Autowired
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

    public Image add(ImageData data) throws ExistsException {
        if (findByLink(data.getImageLink()).isPresent()) throw new ExistsException();
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

    public Image update(Long id, ImageData data) throws NotFoundException {
        Image image = findById(id).orElseThrow(NotFoundException::new);
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

    public void delete(Long id) throws NotFoundException {
        if (imageRepository.findById(id).isPresent()) {
            imageRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    public Image get(Long id) throws NotFoundException {
        return findById(id).orElseThrow(NotFoundException::new);
    }

    public Image getRandom() {
        List<Image> imageSet = findAll();
        Random random = new Random();
        return imageSet.get(random.nextInt(imageSet.size()));
    }
}
