package tai.models.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tai.models.exceptions.ExistsException;
import tai.models.exceptions.NotFoundException;
import tai.models.image.ImageRepository;
import tai.models.image.ImageService;
import tai.models.image_tag.ImageTag;
import tai.models.tags.TagRepository;

import java.util.*;

@Service
@Transactional
public class UserService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final Random random;
    private final ImageService imageService;

    public UserService(ImageRepository imageRepository, TagRepository tagRepository, UserRepository userRepository, ImageService imageService) {
        this.imageRepository = imageRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.random = new Random();
    }

    private Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    private Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public User add(String token) throws ExistsException {
        if (findByToken(token).isPresent()) throw new ExistsException();
        User user = new User();
        Set<ImageTag> tags = new HashSet<>();
        user.setToken(token);
        user.setImageTags(tags);
        return userRepository.save(user);
    }

    public void delete(Long id) throws NotFoundException {
        if (findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    public User get(Long id) throws NotFoundException {
        return findById(id).orElseThrow(NotFoundException::new);
    }

    public int getScore(String token) {
        if(!findByToken(token).isPresent())
            throw new NotFoundException();
        User user = findByToken(token).get();
        Set<ImageTag> imageTags = user.getImageTags();
        int score = 0;
        for (ImageTag imagetag:imageTags) {
            if(imagetag.getTag().equals(imageService.getMostPopularTag(imagetag.getImage())))
                score++;
        }
        return score;
    }

}
