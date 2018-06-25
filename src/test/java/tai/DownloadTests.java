package tai;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import tai.download.DownloadService;
import tai.models.exceptions.NotFoundException;
import tai.models.exceptions.OperationForbiddenException;
import tai.models.image.Image;
import tai.models.image.ImageData;
import tai.models.image.ImageService;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;
import tai.models.user.User;
import tai.models.user.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DownloadTests {
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private ImageService imageService;
    private User user;

    @Before
    @Rollback
    public void setUp() {
        UserRepository userRepository = (UserRepository) ReflectionTestUtils.getField(imageService, "userRepository");
        this.user = new User();
        this.user.setToken("test@mail.com");
        this.user = userRepository.save(user);
        ImageData imageData = new ImageData("link.com");
        Image image = imageService.add(imageData);
        String[] stringList = {"tag1", "tag2"};
        imageData.setTags(Arrays.asList(stringList));
        imageService.update(image.getId(), imageData, "test@mail.com");
    }

    @Test
    @Rollback
    public void testDownloadSuccessful() {
        Map<String, Integer> map = downloadService.getImageLinksWithCounts("tag1", user.getToken());
        Map<String, Integer> correctMap = new HashMap<>();
        correctMap.put("link.com", 1);
        Assert.assertEquals(correctMap, map);
    }

    @Test(expected = OperationForbiddenException.class)
    @Rollback
    public void testDownloadNoSuchUser() {
        downloadService.getImageLinksWithCounts("tag1", generateNonExistentToken());
    }


    @Test(expected = NotFoundException.class)
    @Rollback
    public void testDownloadNoSuchTag() {
        downloadService.getImageLinksWithCounts(generateNonExistentTag(), user.getToken());
    }

    private String generateNonExistentToken() {
        UserRepository userRepository = (UserRepository) ReflectionTestUtils.getField(imageService, "userRepository");
        List<String> userTokens = userRepository.findAll().stream().map(User::getToken).collect(Collectors.toList());
        return generateUUID(userTokens);
    }

    private String generateNonExistentTag() {
        TagRepository tagRepository = (TagRepository) ReflectionTestUtils.getField(imageService, "tagRepository");
        List<String> tagNames = tagRepository.findAll().stream().map(Tag::getTagName).collect(Collectors.toList());
        return generateUUID(tagNames);
    }

    private String generateUUID(Collection<String> collection) {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (collection.contains(uuid.toString()));
        return uuid.toString();
    }

}
