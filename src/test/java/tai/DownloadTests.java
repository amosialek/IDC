package tai;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import tai.download.DownloadController;
import tai.download.DownloadService;
import tai.models.image.ImageRepository;
import tai.models.tags.TagRepository;
import tai.models.user.User;
import tai.models.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadTests {
    @Autowired
    private DownloadController downloadController;
    private DownloadService downloadService;
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private ImageRepository imageRepository;

    @Before
    public void setUp() {
        this.downloadService = (DownloadService) ReflectionTestUtils.getField(downloadController, "downloadService");
        this.userRepository = (UserRepository) ReflectionTestUtils.getField(downloadService, "userRepository");
        this.tagRepository = (TagRepository) ReflectionTestUtils.getField(downloadService, "tagRepository");
        this.imageRepository = (ImageRepository) ReflectionTestUtils.getField(downloadService, "imageRepository");
        User user = new User();
        user.setEmail("mail@mail.com");
        user = userRepository.save(user);
    }
}
