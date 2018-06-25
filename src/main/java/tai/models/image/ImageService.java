package tai.models.image;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tai.models.exceptions.ExistsException;
import tai.models.exceptions.NotFoundException;
import tai.models.image_tag.ImageTag;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;
import tai.models.user.User;
import tai.models.user.UserRepository;

import java.util.*;

@Service
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final Random random;

    public ImageService(ImageRepository imageRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.random = new Random();
    }

    private Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    private Optional<Image> findByLink(String link) {
        return imageRepository.findByImageLink(link);
    }

    private Optional<Tag> findByTag(String tag) {
        return tagRepository.findByTagName(tag);
    }

    private Optional<User> findUserByToken(String token){
        return userRepository.findByToken(token);
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

    public void update(Long id, ImageData data, String principalName) throws NotFoundException {
        Image image = findById(id).orElseThrow(NotFoundException::new);
        //wyciagnij uzytkownika z bazy jesli istnieje, jesli nie istnieje - utworz
        User user = findUserByToken(principalName).orElseGet(() ->
                userRepository.save(new User(principalName))
        );
        Set<ImageTag> tags = image.getImageTags();
        if (tags == null)
            tags = new HashSet<>();
        for (String tag : data.getTags()) {
            if (findByTag(tag).isPresent()) {
                //tag istnieje w bazie
                boolean flag = true;
                for (ImageTag imageTag : image.getImageTags()) {
                    if (imageTag.getTag().getTagName().equals(tag)) {
                        imageTag.setCount(imageTag.getCount() + 1);
                        flag = false;
                        break;
                    }
                    user.addImageTag(imageTag);
                }
                //tag istnieje w bazie, ale nie jest powiazany z obrazkiem
                if (flag) {
                    tags.add(new ImageTag(image, findByTag(tag).get()));
                }
            } else {
                //tag nie istnieje w bazie, zatem nie jest tez powiazany z obrazkiem
                Tag tagObject = new Tag(tag);
                tagRepository.save(tagObject);
                System.out.println(findByTag(tag).isPresent());
                ImageTag imageTag = new ImageTag(image, tagObject);
                tags.add(imageTag);
                user.addImageTag(imageTag);
            }
        }

        image.setImageLink(data.getImageLink());
        image.setImageTags(tags);
        imageRepository.save(image);
    }

    public void delete(Long id) throws NotFoundException {
        if (findById(id).isPresent()) {
            imageRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    public Image get(Long id) throws NotFoundException {
        return findById(id).orElseThrow(NotFoundException::new);
    }

    public Image getRandomImageFromDatabase() {
        List<Image> imageList = findAll();
        return imageList.get(random.nextInt(imageList.size()));
    }

    public Tag getMostPopularTag(Image image){
        return image.getImageTags().stream().max(Comparator.comparing(ImageTag::getCount)).get().getTag();
    }

    private List<Image> findAll() {
        return imageRepository.findAll();
    }
}
