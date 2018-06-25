package tai.download;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tai.models.exceptions.NotFoundException;
import tai.models.exceptions.OperationForbiddenException;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;
import tai.models.user.UserRepository;

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

    private boolean doesUserExists(String principalName) {
        return userRepository.findByToken(principalName).isPresent();
    }

    public Map<String, Integer> getImageLinksWithCounts(String tagName, String principalName) throws NotFoundException,
            OperationForbiddenException {
        if (!doesUserExists(principalName)) throw new OperationForbiddenException();
        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(NotFoundException::new);
        Map<String, Integer> resultImages = new HashMap<>();
        tag.getImageTags().stream().filter(imageTag -> imageTag.getCount() > 0)
                .forEach(imageTag -> resultImages.put(imageTag.getImage().getImageLink(),
                        imageTag.getCount()));
        return resultImages;
    }

}
