package tai.download;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tai.models.exceptions.NotFoundException;
import tai.models.exceptions.OperationForbiddenException;
import tai.models.image_tag.ImageTag;
import tai.models.image_tag.ImageTagRepository;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;
import tai.models.user.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class DownloadService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ImageTagRepository imageTagRepository;

    public DownloadService(TagRepository tagRepository, UserRepository userRepository, ImageTagRepository imageTagRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.imageTagRepository = imageTagRepository;
    }

    private boolean doesUserExists(String principalName) {
        return userRepository.findByToken(principalName).isPresent();
    }

    public Map<String, Integer> getImageLinksWithCounts(String tagName, String principalName) throws NotFoundException,
            OperationForbiddenException {
        if (!doesUserExists(principalName)) throw new OperationForbiddenException();
        Tag tag = tagRepository.findByTagName(tagName).orElseThrow(NotFoundException::new);
        List<ImageTag> imageTagSet = imageTagRepository.findAll().stream().filter(imageTag -> imageTag.getTag().equals(tag))
                .collect(Collectors.toList());
        Map<String, Integer> resultImages = new HashMap<>();
        imageTagSet.stream().filter(imageTag -> imageTag.getCount()>0)
                .forEach(imageTag -> resultImages.put(imageTag.getImage().getImageLink(),
                        imageTag.getCount()));
        return resultImages;
    }

}
