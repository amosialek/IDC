package tai.models.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tai.models.user.AuthenticationException;

import javax.validation.Valid;
import java.security.Principal;

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
    public ResponseEntity update(Long id, @Valid @RequestBody ImageData imageData, Principal principal){
        System.out.println(imageData.getTags().get(0));
        try {
            imageService.update(id, imageData, principal.getName());
            return new ResponseEntity(HttpStatus.OK);
        }
        catch(AuthenticationException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
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
