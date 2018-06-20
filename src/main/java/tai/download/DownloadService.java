package tai.download;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tai.models.exceptions.NotFoundException;
import tai.models.exceptions.OperationForbiddenException;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;
import tai.models.user.User;
import tai.models.user.UserRepository;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class DownloadService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public DownloadService(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    private User findUserByEmail(@Email String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    private Map<String, Integer> findImagesByTag(String tagName) throws NotFoundException {
        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(NotFoundException::new);
        Map<String, Integer> resultImages = new HashMap<>();
        tag.getImageTags().stream().filter(imageTag -> imageTag.getCount() > 0).forEach(imageTag -> resultImages.put(imageTag.getImage().getImageLink(),
                imageTag.getCount()));
        return resultImages;
    }

    public Map<String, Integer> getImageLinksWithCounts(String tagName, String email) throws NotFoundException, OperationForbiddenException {
        try {
            findUserByEmail(email);
        } catch (NotFoundException e) {
            throw new OperationForbiddenException();
        }
        return findImagesByTag(tagName);
    }

}
