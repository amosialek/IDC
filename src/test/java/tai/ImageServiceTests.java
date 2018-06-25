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
import tai.models.exceptions.NotFoundException;
import tai.models.image.Image;
import tai.models.image.ImageData;
import tai.models.image.ImageRepository;
import tai.models.tags.TagRepository;
import tai.models.user.UserRepository;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ImageServiceTests {
    @Autowired
    private tai.models.image.ImageService imageService;

    private tai.models.image.ImageData imageData;

    @Before
    public void setUp() {
        this.imageData = new tai.models.image.ImageData("mock_link.com");
    }

    @Test
    @Rollback
    public void testAddImageSuccess() {
        Assert.assertEquals(imageService.add(imageData).getImageLink(), imageData.getImageLink());
    }

    @Test(expected = tai.models.exceptions.ExistsException.class)
    @Rollback
    public void testAddImageAlreadyExists() {
        imageService.add(imageData);
        imageService.add(imageData);
    }

    @Test
    @Rollback
    public void testUpdateImageSuccess() {
        Image image = imageService.add(imageData);
        ImageData updatedImageData = new ImageData("new_link.com");
        List<String> tags = new LinkedList<>();
        tags.add("test_tag");
        updatedImageData.setTags(tags);
        imageService.update(image.getId(), updatedImageData, "mail@mail.com");
//                updatedImageData.getImageLink());
        TagRepository tagRepository = (TagRepository) ReflectionTestUtils.getField(imageService, "tagRepository");
        Assert.assertTrue(tagRepository.findByTagName("test_tag").isPresent());
    }

    @Test(expected = NotFoundException.class)
    @Rollback
    public void testUpdateImageNoSuchImage() {
        Long imageIdNotInDatabase = getImageIdNotInDatabase();
        ImageData updatedImageData = new ImageData("new_link.com");
        imageService.update(imageIdNotInDatabase, updatedImageData, "mail@mail.com");
    }

    @Test
    @Rollback
    public void testDeleteImageSuccess() {
        ImageRepository imageRepository = (ImageRepository) ReflectionTestUtils.getField(imageService, "imageRepository");
        Image createdImage = new Image();
        createdImage.setImageLink(imageData.getImageLink());
        Image image = imageRepository.save(createdImage);
        imageService.delete(image.getId());
        Assert.assertFalse(imageRepository.findByImageLink(image.getImageLink()).isPresent());
    }

    @Test(expected = NotFoundException.class)
    @Rollback
    public void testDeleteImageNoSuchImage() {
        Long imageIdNotInDatabase = getImageIdNotInDatabase();
        imageService.delete(imageIdNotInDatabase);
    }

    @Test
    @Rollback
    public void testGetRandomImage() {
        ImageRepository imageRepository = (ImageRepository) ReflectionTestUtils.getField(imageService, "imageRepository");
        imageRepository.save(new Image("link"));
        List<Image> imageList = imageRepository.findAll();
        Assert.assertTrue(imageList.contains(imageService.getRandomImageFromDatabase()));
    }

    @Test
    @Rollback
    public void testGetImageSuccess() {
        ImageRepository imageRepository = (ImageRepository) ReflectionTestUtils.getField(imageService, "imageRepository");
        Image image = imageRepository.save(new Image("link"));
        Assert.assertEquals(imageService.get(image.getId()), image);
    }

    @Test(expected = NotFoundException.class)
    @Rollback
    public void testGetImageNoSuchImage(){
        Long imageIdNotInDatabase = getImageIdNotInDatabase();
        imageService.get(imageIdNotInDatabase);
    }

    private Long getImageIdNotInDatabase(){
        tai.models.image.ImageRepository imageRepository = (tai.models.image.ImageRepository) ReflectionTestUtils.getField(imageService, "imageRepository");
        List<Long> ids = imageRepository.findAll().stream().map(tai.models.image.Image::getId).collect(Collectors.toList());
        Long idNotInDatabase;
        Random random = new Random();
        do {
            idNotInDatabase = random.nextLong();
        } while (ids.contains(idNotInDatabase));
        return idNotInDatabase;
    }

}
