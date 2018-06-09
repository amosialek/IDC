package tai.models.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tai.imgur.ImgurAPI;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/image/random")
@Transactional
public class RandomImageController {
    private final ImageService imageService;

    @Autowired
    public RandomImageController(ImageService imageService) {
        this.imageService = imageService;


    }
    @GetMapping
    public Image get(){
        return imageService.getRandomImageFromDatabase();
    }

    @PutMapping
    public void add(){
        ImgurAPI imgurAPI = new ImgurAPI();
        List<String> urls = imgurAPI.getRandomImagesURLs();
        for (String url:urls) {
            imageService.add(new ImageData(url));
        }
    }
}
