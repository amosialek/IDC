package tai.models.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/image")
@Transactional
public class ImageController {
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PutMapping
    public Image add(@Valid @RequestBody ImageData imageData){
        return imageService.add(imageData);
    }

    @CrossOrigin
    @PostMapping(consumes = {"application/json;charset=UTF-8"},produces = "application/json")
    public Image update(Long id, @Valid @RequestBody ImageData imageData){
        System.out.println(imageData.getTags().get(0));
        return imageService.update(id, imageData);
    }

    @DeleteMapping
    public void delete(Long id){
        imageService.delete(id);
    }

    @GetMapping
    public Image get(Long id){
        return imageService.get(id);
    }
}
