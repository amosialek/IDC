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
import tai.models.image.Image;
import tai.models.image.ImageData;
import tai.models.image.ImageRepository;
import tai.models.image.ImageService;
import tai.models.tags.Tag;
import tai.models.tags.TagRepository;
import tai.models.user.User;
import tai.models.user.UserRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DownloadTests {
    /*@Autowired
    private DownloadService downloadService;
    @Autowired
    private ImageService imageService;
    private Image image;
    private User user;

    @Before
    public void setUp() {
        ImageData imageData = new ImageData("link.com");
        this.image = imageService.add(imageData);
        String[] stringList = {"tag1", "tag2"};
        imageData.setTags(Arrays.asList(stringList));
        imageService.update(image.getId(), imageData, "mail@mail.com");
        UserRepository userRepository = (UserRepository) ReflectionTestUtils.getField(imageService, "userRepository");
        this.user = userRepository.findByToken("mail@mail.com").get();
        this.user.setToken("mail@mail.com");
    }

    @Test
    @Rollback
    public void testDownloadSuccessful() {
        Map<String, Integer> map = downloadService.getImageLinksWithCounts("tag1", user.getToken());
        Map<String, Integer> correctMap = new HashMap<>();
        correctMap.put("tag1", 1);
        System.out.println(imageService.get(image.getId()).getImageLink());
        TagRepository tagRepository = (TagRepository) ReflectionTestUtils.getField(downloadService, "tagRepository");
        tagRepository.findAll().stream().map(Tag::getTagName).forEach(System.out::println);
        ImageRepository imageRepository = (ImageRepository) ReflectionTestUtils.getField(imageService, "imageRepository");
        imageRepository.findAll().stream().map(Image::getImageLink).forEach(System.out::println);
        Assert.assertEquals(correctMap, map);
    }*/
}
